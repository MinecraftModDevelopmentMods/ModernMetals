// Deterministic supplements to the hand-authored 1.21.1 resources.
const fs = require('fs');
const path = require('path');
const root = path.resolve(__dirname, '..');
const source = path.join(root, 'src/main/resources');
const output = path.join(root, 'build/generated/modernization');
if (!output.startsWith(path.join(root, 'build') + path.sep)) throw Error('Unsafe output');
fs.rmSync(output, { recursive: true, force: true });
const read = p => JSON.parse(fs.readFileSync(path.join(source, p), 'utf8'));
const write = (p, data) => {
  const file = path.join(output, p);
  fs.mkdirSync(path.dirname(file), { recursive: true });
  fs.writeFileSync(file, JSON.stringify(data, null, 2) + '\n');
};
function files(dir) {
  if (!fs.existsSync(dir)) return [];
  return fs.readdirSync(dir, { withFileTypes: true }).sort((a,b)=>a.name.localeCompare(b.name))
    .flatMap(e => e.isDirectory() ? files(path.join(dir,e.name)) : e.name.endsWith('.json') ? [path.join(dir,e.name)] : []);
}
const materials = fs.readdirSync(path.join(source,'mmdlib/material')).filter(n=>n.endsWith('.json')).sort()
  .map(file=>({ name:file.slice(0,-5), ...read('mmdlib/material/'+file) }));
if (materials.length !== 21) throw Error('Incomplete material catalogue');
const policy = JSON.parse(fs.readFileSync(path.join(__dirname,'material-policy.json')));
const recipes = new Map(files(path.join(source,'data/modernmetals/recipe')).map(file=>[
  path.relative(path.join(source,'data/modernmetals/recipe'),file).replaceAll('\\','/').slice(0,-5), JSON.parse(fs.readFileSync(file))]));
const condition = item => ({ type:'modernmetals:content_allowed', item });
const standalone = { type:'neoforge:not', value:{ type:'neoforge:mod_loaded', modid:'basemetals' } };
function add(id, recipe) {
  if (recipes.has(id)) throw Error('Duplicate recipe '+id);
  if (!recipe['neoforge:conditions'] && recipe.type !== 'mmdlib:crushing' && recipe.type !== 'modernmetals:plate_repair') {
    recipe['neoforge:conditions'] = [condition(recipe.result.id)];
  }
  recipes.set(id, recipe);
  write('data/modernmetals/recipe/'+id+'.json',recipe);
}
function tag(registry, id, values) { const [ns,...parts]=id.split(':'); write('data/'+ns+'/tags/'+registry+'/'+parts.join(':')+'.json',{replace:false,values}); }
const id = (name,form) => 'modernmetals:'+name+'_'+form;
const ingredient = item => item.startsWith('#') ? {tag:item.slice(1)} : {item};
function cooking(name,input,result,count=1,method='smelting',extra=[]) {
  add(name,{type:'minecraft:'+method,category:'misc',ingredient:ingredient(input),result:{id:result,count},
    experience:0.05,cookingtime:method==='blasting'?100:200,'neoforge:conditions':[condition(result),...extra]});
}
const byOutput = new Map();
for(const r of recipes.values()) if(r.type.startsWith('minecraft:crafting_')) {
  if(!byOutput.has(r.result.id)) byOutput.set(r.result.id,[]);
  byOutput.get(r.result.id).push(r);
}
function units(material, form, seen=[]) {
  const simple={ingot:9,nugget:1,powder:9,smallpowder:1,block:81};
  if(simple[form]) return simple[form];
  if(seen.includes(form)) return 0;
  const values=(byOutput.get(id(material,form))||[]).map(r=> {
    const ingredients=r.pattern ? r.pattern.flatMap(row=>[...row].filter(k=>k!==' ').map(k=>r.key[k])) : r.ingredients;
    return ingredients.reduce((sum,i)=>sum+ingredientUnits(material,i,[...seen,form]),0)/(r.result.count||1);
  }).filter(x=>x>0);
  return values.length ? Math.min(...values) : 0;
}
function ingredientUnits(material,value,seen) {
  if(Array.isArray(value)) return Math.min(...value.map(v=>ingredientUnits(material,v,seen)));
  if(value.type==='neoforge:compound') return Math.min(...value.children.map(v=>ingredientUnits(material,v,seen)));
  const t=(value.tag||'').match(/^c:([^/]+)\/([a-z]+)$/);
  const forms={ingots:'ingot',nuggets:'nugget',storage_blocks:'block',rods:'rod',gears:'gear',plates:'plate'};
  if(t && t[2]===material && forms[t[1]]) return units(material,forms[t[1]],seen);
  const v=(value.item||'').match(/^modernmetals:([a-z]+)_(.+)$/);
  return v && v[1]===material ? units(material,v[2],seen) : 0;
}
const recycling=['axe','boots','helmet','sword','shovel','pickaxe','hoe','leggings','chestplate','crackhammer','shears',
  'rod','gear','slab','button','lever','wall','pressure_plate','door','trapdoor','horse_armor','fishing_rod','bars','plate','bow','crossbow','arrow','bolt','scythe','shield'];
const mining={mineable:[],stone:[],iron:[],diamond:[]};
const costs={};
for(const m of materials) {
  const name=m.name;
  costs[name]={};
  for(const form of recycling) {
    const actual=units(name,form);
    if(!actual) throw Error('No material cost: '+name+' '+form);
    let value=Math.floor(actual+1e-8);
    if(['bow','fishing_rod','lever'].includes(form)) value=Math.min(value,9);
    if(form==='arrow') value=Math.min(value,1);
    if(form==='bolt') value=Math.min(value,2);
    let result=value>=9?'ingot':'nugget';
    let count=value>=9?Math.floor(value/9):value;
    if(['wall','crackhammer'].includes(form) && value>=81) {result='block';count=Math.floor(value/81);}
    if(count<1 || count>64) throw Error('Invalid recycling count '+name+' '+form);
    const returned=count*({nugget:1,ingot:9,block:81}[result]);
    if(returned>actual+1e-8) throw Error('Material gain in '+name+' '+form);
    costs[name][form]={cost_nuggets:actual,returned_nuggets:returned};
    cooking('smelting/'+name+'_'+form+'_recycling',id(name,form),id(name,result),count);
  }
  for(const form of ['helmet','chestplate','leggings','boots','shield'])
    add(name+'_'+form+'_plate_repair',{type:'modernmetals:plate_repair',target:id(name,form),plate:{tag:'c:plates/'+name}});
  const nativeOre=m.blocks.split(' ').includes('ore');
  for(const form of m.blocks.split(' ').map(x=>x.split(':').at(-1))) {
    const block=id(name,form);
    mining.mineable.push(block);
    const level=policy[name][form==='ore'?'ore':form==='raw_block'?'ore':'solid'];
    if(level>0) mining[['','stone','iron','diamond'][level]].push(block);
  }
  if(nativeOre) {
    const raw=id(name,'raw'), block=id(name,'raw_block');
    tag('item','c:raw_materials/'+name,[raw]);
    tag('item','c:storage_blocks/raw_'+name,[block]);
    tag('block','c:storage_blocks/raw_'+name,[block]);
    add(name+'_raw_block',{type:'minecraft:crafting_shaped',category:'building',pattern:['xxx','xxx','xxx'],key:{x:{tag:'c:raw_materials/'+name}},result:{id:block}});
    add(name+'_raw_block_unpack',{type:'minecraft:crafting_shapeless',category:'misc',ingredients:[{item:block}],result:{id:raw,count:9}});
    add(name+'_raw_crushing',{type:'mmdlib:crushing',ingredient:{tag:'c:raw_materials/'+name},result:{id:id(name,'powder'),count:1}});
    add(name+'_raw_block_crushing',{type:'mmdlib:crushing',ingredient:{item:block},result:{id:id(name,'powder'),count:9}});
    for(const method of ['smelting','blasting']) cooking(name+'_raw_'+method,'#c:raw_materials/'+name,id(name,'ingot'),1,method);
    const silk={type:'minecraft:item',name:id(name,'ore'),conditions:[{condition:'minecraft:match_tool',predicate:{predicates:{'minecraft:enchantments':[{enchantments:'minecraft:silk_touch',levels:{min:1}}]}}}]};
    const drop={type:'minecraft:item',name:raw,functions:[{function:'minecraft:apply_bonus',enchantment:'minecraft:fortune',formula:'minecraft:ore_drops'},{function:'minecraft:explosion_decay'}]};
    write('data/modernmetals/loot_table/blocks/'+name+'_ore.json',{type:'minecraft:block',pools:[{rolls:1,entries:[{type:'minecraft:alternatives',children:[silk,drop]}]}]});
    write('data/modernmetals/loot_table/blocks/'+name+'_raw_block.json',{type:'minecraft:block',pools:[{rolls:1,entries:[{type:'minecraft:item',name:block}],conditions:[{condition:'minecraft:survives_explosion'}]}]});
    write('assets/modernmetals/models/item/'+name+'_raw.json',{parent:'minecraft:item/generated',textures:{layer0:'modernmetals:item/'+name+'_raw'}});
    write('assets/modernmetals/models/block/'+name+'_raw_block.json',{parent:'minecraft:block/cube_all',textures:{all:'modernmetals:block/'+name+'_raw_block'}});
    write('assets/modernmetals/models/item/'+name+'_raw_block.json',{parent:'modernmetals:block/'+name+'_raw_block'});
    write('assets/modernmetals/blockstates/'+name+'_raw_block.json',{variants:{'':{model:'modernmetals:block/'+name+'_raw_block'}}});
  }
}
for(const [key,name] of [['stone','needs_stone_tool'],['iron','needs_iron_tool'],['diamond','needs_diamond_tool']]) tag('block','minecraft:'+name,mining[key]);
tag('block','minecraft:mineable/pickaxe',mining.mineable);
tag('item','c:raw_materials',materials.filter(m=>m.blocks.split(' ').includes('ore')).map(m=>'#c:raw_materials/'+m.name));
for(const registry of ['item','block']) {
  const previous=read('data/c/tags/'+registry+'/storage_blocks.json').values;
  tag(registry,'c:storage_blocks',[...previous,...materials.filter(m=>m.items.split(' ').includes('modernmetals:raw')).map(m=>'#c:storage_blocks/raw_'+m.name)]);
}
const extras=['galvanizedsteel','nichrome','stainlesssteel'];
for(const name of extras) {
  const ore=id(name,'ore'); mining.mineable.push(ore); mining.stone.push(ore);
  const model=read('assets/modernmetals/models/block/aluminum_ore.json');
  for(const element of model.elements) for(const face of Object.values(element.faces)) if(face.texture==='#overlay') face.tintindex=0;
  write('assets/modernmetals/models/block/'+name+'_ore.json',model);
  write('assets/modernmetals/models/item/'+name+'_ore.json',{parent:'modernmetals:block/'+name+'_ore'});
  write('assets/modernmetals/blockstates/'+name+'_ore.json',{variants:{'':{model:'modernmetals:block/'+name+'_ore'}}});
  write('data/modernmetals/loot_table/blocks/'+name+'_ore.json',{type:'minecraft:block',pools:[{rolls:1,entries:[{type:'minecraft:item',name:ore}]}]});
}
tag('block','minecraft:mineable/pickaxe',mining.mineable);
tag('block','minecraft:needs_stone_tool',mining.stone);
for(const m of materials) write('assets/modernmetals/models/item/'+m.name+'.json',read('assets/modernmetals/models/item/'+m.name+'_molten_bucket.json'));
tag('item','c:dusts/coal',['modernmetals:coal_powder']);
write('assets/modernmetals/models/item/coal_powder.json',{parent:'minecraft:item/generated',textures:{layer0:'modernmetals:item/coal_powder'}});
for(const [name,input,count] of [['coal','minecraft:coal',1],['coal_block','minecraft:coal_block',9],['coal_ore','#c:ores/coal',2]])
  add('crushing/'+name,{type:'mmdlib:crushing','neoforge:conditions':[standalone],ingredient:ingredient(input),result:{id:'modernmetals:coal_powder',count}});
add('coal_block_dust',{type:'minecraft:crafting_shaped',category:'misc','neoforge:conditions':[standalone],pattern:['xxx','xxx','xxx'],key:{x:{tag:'c:dusts/coal'}},result:{id:'minecraft:coal_block'}});
for(const name of ['copper','steel','zinc','nickel']) tag('item','c:dusts/'+name,[]);
tag('item','c:blends/steel',[]);
const optional=(tagName)=>({id:'#'+tagName,required:false});
for(const m of materials) {
  if(m.items.split(' ').includes('blend')) tag('item','c:blends/'+m.name,[id(m.name,'blend')]);
}
for(const [name,aliases] of Object.entries({aluminum:['aluminium','bauxite'],aluminumbrass:['aluminiumbrass','alubrass'],galvanizedsteel:['galvinizedsteel'],tungsten:['wolfram']}))
  for(const category of ['dusts','ingots','nuggets','raw_materials']) for(const alias of aliases) tag('item','c:'+category+'/'+alias,[optional('c:'+category+'/'+name)]);
tag('item','c:dusts/chrome',['modernmetals:chromium_powder']);
let unlocks=0;
for(const [name,r] of recipes) {
  if(!r.type.startsWith('minecraft:crafting_') && !['minecraft:smelting','minecraft:blasting'].includes(r.type)) continue;
  const criteria={has_recipe:{trigger:'minecraft:recipe_unlocked',conditions:{recipe:'modernmetals:'+name}}};
  const mat=name.startsWith('smelting/')?name.split('/')[1].split('_')[0]:name.split('_')[0];
  if(r.type.startsWith('minecraft:crafting_') && policy[mat]) criteria.has_material={trigger:'minecraft:inventory_changed',conditions:{items:[{items:'#c:ingots/'+mat}]}};
  function flatten(v) {return Array.isArray(v)?v.flatMap(flatten):v?.type==='neoforge:compound'?v.children.flatMap(flatten):v?[v]:[];}
  const inputs=flatten(r.key?Object.values(r.key):r.ingredients||r.ingredient);
  const relevant=inputs.filter(v=>!['minecraft:stick','minecraft:string','minecraft:feather'].includes(v.item) && v.tag!=='c:rods/wooden');
  const unique=new Map((relevant.length?relevant:inputs).map(v=>[JSON.stringify(v),v]));
  let index=0;
  for(const v of unique.values()) if(v.item||v.tag) {
    const key=name.endsWith('_blend_base') && v.tag?.startsWith('c:blends/')?'has_blend_'+v.tag.split('/')[1]:'has_ingredient_'+(++index);
    criteria[key]={trigger:'minecraft:inventory_changed',conditions:{items:[{items:v.item||'#'+v.tag}]}};
  }
  const adv={parent:'minecraft:recipes/root',criteria,requirements:[Object.keys(criteria)],rewards:{recipes:['modernmetals:'+name]},'neoforge:conditions':r['neoforge:conditions']||[]};
  write('data/modernmetals/advancement/recipes/'+name+'.json',adv);unlocks++;
  // Keep earned 1.13 furnace advancement IDs readable after the renamed recipe.
  const match=name.match(/^([a-z]+)_(ore|powder|blend|smallpowder|smallblend)_smelting$/);
  if(match) write('data/modernmetals/advancement/recipes/smelting/'+match[1]+'_'+match[2]+'.json',adv);
}
write('modernmetals-processing-costs.json',costs);
const localized=JSON.parse(fs.readFileSync(path.join(__dirname,'material-names.json')));
const genitive={aluminum:'алюминия',beryllium:'бериллия',boron:'бора',cadmium:'кадмия',chromium:'хрома',iridium:'иридия',magnesium:'магния',manganese:'марганца',osmium:'осмия',plutonium:'плутония',rutile:'рутила',tantalum:'тантала',thorium:'тория',titanium:'титана',tungsten:'вольфрама',uranium:'урана',zirconium:'циркония'};
for(const file of fs.readdirSync(path.join(source,'assets/modernmetals/lang')).filter(n=>n.endsWith('.json')).sort()) {
  const locale=file.slice(0,-5), language=read('assets/modernmetals/lang/'+file);
  const kind=locale.startsWith('en_')?'en':locale;
  for(const m of materials) {
    const n=localized[locale]?.[m.name] || language['item.modernmetals.'+m.name+'_ingot'].replace(/ Ingot$/,'');
    const item=(suffix,text)=>language['item.modernmetals.'+m.name+suffix]=text;
    const block=(suffix,text)=>language['block.modernmetals.'+m.name+suffix]=text;
    let bucket, casing, sickle, raw, rawBlock;
    if(kind==='de_de') {bucket='Schmelze: '+n+' (Eimer)';casing='Gehäuse ('+n+')';sickle='Sichel ('+n+')';raw=n+'-Erzbrocken';rawBlock='Block aus '+n+'-Erzbrocken';}
    else if(kind==='ru_ru') {bucket='Ведро расплава: '+n;casing='Корпус ('+n+')';sickle='Серп ('+n+')';raw='Необработанный '+n;rawBlock='Блок необработанного '+(genitive[m.name]||n);}
    else if(kind==='zh_cn') {bucket='熔融'+n+'桶';casing=n+'外壳';sickle=n+'镰刀';raw='粗'+n;rawBlock='粗'+n+'块';}
    else {bucket='Molten '+n+' Bucket';casing=n+' Item Casing';sickle=n+' Sickle';raw='Raw '+n;rawBlock='Block of Raw '+n;}
    item('_molten_bucket',bucket); item('',bucket);
    block('_molten',bucket.replace(/ Bucket$| \(Eimer\)$|桶$/,''));
    if(!language['item.modernmetals.'+m.name+'_casing']) item('_casing',casing);
    if(!language['item.modernmetals.'+m.name+'_scythe']) item('_scythe',sickle);
    if(m.items.split(' ').includes('modernmetals:raw')) {item('_raw',raw);block('_raw_block',rawBlock);}
    if(extras.includes(m.name) && !language['block.modernmetals.'+m.name+'_ore']) block('_ore',n+' Ore');
  }
  language['item.modernmetals.coal_powder']=kind==='de_de'?'Kohlestaub':kind==='ru_ru'?'Угольная пыль':kind==='zh_cn'?'煤粉':'Coal Dust';
  language['modernmetals.configuration.moltenMetalBurns']=kind==='de_de'?'Verbrennungen durch Metallschmelze':kind==='ru_ru'?'Ожоги от расплавленного металла':kind==='zh_cn'?'熔融金属造成灼伤':'Molten Metal Burns';
  write('assets/modernmetals/lang/'+file,Object.fromEntries(Object.entries(language).sort(([a],[b])=>a.localeCompare(b))));
}
console.log('Generated '+unlocks+' recipe advancements and '+(recipes.size-1099)+' supplemental recipes; four-rod accounting checked.');
