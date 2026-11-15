// Deterministic resource checks. Gameplay and codecs are covered by the native tests.
const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const root=path.resolve(__dirname,'..'), main=path.join(root,'src/main/resources'), gen=path.join(root,'build/generated/modernization');
function files(dir){return fs.readdirSync(dir,{withFileTypes:true}).flatMap(e=>e.isDirectory()?files(path.join(dir,e.name)):[path.join(dir,e.name)]);}
function read(name){return JSON.parse(fs.readFileSync(fs.existsSync(path.join(gen,name))?path.join(gen,name):path.join(main,name),'utf8').replace(/^\uFEFF/,''));}
const materials=files(path.join(main,'mmdlib/material')).map(f=>({...JSON.parse(fs.readFileSync(f,'utf8')),name:path.basename(f,'.json')}));
test('all source and generated JSON parse',()=>{for(const dir of [main,gen])for(const f of files(dir).filter(f=>f.endsWith('.json')))JSON.parse(fs.readFileSync(f,'utf8').replace(/^\uFEFF/,''));});
test('seventeen raw chains, loot and pixel assets',()=>{
 const ores=materials.filter(m=>m.blocks.split(' ').includes('ore')); assert.equal(ores.length,17);
 for(const m of ores){
  assert(m.items.split(' ').includes('modernmetals:raw'));
  const loot=JSON.stringify(read(`data/modernmetals/loot_table/blocks/${m.name}_ore.json`));
  for(const token of [`modernmetals:${m.name}_raw`,'minecraft:silk_touch','minecraft:fortune'])assert(loot.includes(token),m.name+token);
  for(const [suffix,count] of [['_raw_crushing',1],['_raw_block_crushing',9],['_raw_block_unpack',9]])assert.equal(read(`data/modernmetals/recipe/${m.name}${suffix}.json`).result.count,count);
  for(const asset of [`item/${m.name}_raw`,`block/${m.name}_raw_block`]){const png=fs.readFileSync(path.join(main,'assets/modernmetals/textures',asset+'.png'));assert.equal(png.readUInt32BE(16),16);assert.equal(png.readUInt32BE(20),16);assert.equal(png[25],6);}
 }
});
test('whole steel blend and chrome alternatives remain guarded',()=>{
 for(const m of ['galvanizedsteel','stainlesssteel'])assert(JSON.stringify(read(`data/modernmetals/recipe/${m}_blend_base.json`)).includes('c:blends/steel'));
 for(const m of ['nichrome','stainlesssteel'])assert(JSON.stringify(read(`data/modernmetals/recipe/${m}_blend_base.json`)).includes('c:dusts/chrome'));
});
test('component yields and recycling stay bounded',()=>{
 for(const m of materials){for(const [form,count] of [['rod',4],['gear',4],['plate',3]])assert.equal(read(`data/modernmetals/recipe/${m.name}_${form}.json`).result.count,count);}
 const r=read('data/modernmetals/recipe/smelting/aluminum_rod_recycling.json');assert.equal(r.result.id,'modernmetals:aluminum_nugget');assert.equal(r.result.count,4);
});
test('all book recipes have guarded stable advancements',()=>{
 const recipes=new Map();for(const dir of [main,gen])for(const f of files(path.join(dir,'data/modernmetals/recipe')))recipes.set(path.relative(path.join(dir,'data/modernmetals/recipe'),f).replaceAll('\\','/'),JSON.parse(fs.readFileSync(f,'utf8')));
 let count=0;for(const [name,r] of recipes){if(!r.type.startsWith('minecraft:crafting_')&&!['minecraft:smelting','minecraft:blasting'].includes(r.type))continue;count++;const a=read('data/modernmetals/advancement/recipes/'+name);assert.deepEqual(a.rewards.recipes,['modernmetals:'+name.slice(0,-5)]);assert.deepEqual(a['neoforge:conditions']||[],r['neoforge:conditions']||[]);}assert(count>1600);
});
test('standalone coal recipes have exclusive ownership',()=>{for(const p of ['coal_block_dust','crushing/coal','crushing/coal_block','crushing/coal_ore'])assert(read('data/modernmetals/recipe/'+p+'.json')['neoforge:conditions'].some(c=>c.type==='neoforge:not'&&c.value.type==='neoforge:mod_loaded'&&c.value.modid==='basemetals'));});
test('independent mining levels and dynamic repair',()=>{assert.equal(read('mmdlib/material/galvanizedsteel.json').runtime.incorrect_blocks,'minecraft:incorrect_for_iron_tool');assert.equal(read('data/modernmetals/recipe/aluminum_helmet_plate_repair.json').type,'modernmetals:plate_repair');});
test('all locales name raw materials and molten buckets',()=>{
 const langs=files(path.join(gen,'assets/modernmetals/lang'));assert.equal(langs.length,9);
 for(const f of langs){const l=JSON.parse(fs.readFileSync(f,'utf8'));for(const m of materials){assert(l[`item.modernmetals.${m.name}_molten_bucket`],f+m.name);assert(l[`block.modernmetals.${m.name}_molten`]);if(m.items.includes('modernmetals:raw')){assert(l[`item.modernmetals.${m.name}_raw`]);assert(l[`block.modernmetals.${m.name}_raw_block`]);}}}
});
test('ore overlay faces cover the complete sprite',()=>{for(const f of files(path.join(main,'assets/modernmetals/models/block')).filter(f=>f.endsWith('_ore.json')))for(const e of JSON.parse(fs.readFileSync(f,'utf8')).elements||[])for(const face of Object.values(e.faces))if(face.texture==='#overlay')assert.deepEqual(face.uv,[0,0,16,16],f);});
test('OreSpawn rule IDs retain saved 1.13 settings',()=>{
 const provider=read('data/modernmetals/orespawn/provider.json');
 assert.equal(Object.keys(provider.ores).length,17);
 for(const [key,ore] of Object.entries(provider.ores))assert.equal(key,'modernmetals:legacy/'+ore.block.split(':')[1]);
 assert.equal(provider.ores['modernmetals:legacy/titanium_ore'].enabled,false);
});
test('all old furnace advancement paths remain readable',()=>{
 for(const m of materials)for(const form of ['ore','powder','blend','smallpowder','smallblend']) {
  if(fs.existsSync(path.join(main,`data/modernmetals/recipe/${m.name}_${form}_smelting.json`)))assert(read(`data/modernmetals/advancement/recipes/smelting/${m.name}_${form}.json`));
 }
});
test('generated replacements are limited to the audited resource families',()=>{
 const allowed=/^(assets\/modernmetals\/lang\/[^/]+\.json|data\/c\/tags\/(item|block)\/storage_blocks\.json|data\/minecraft\/tags\/block\/(needs_(stone|iron)_tool|mineable\/pickaxe)\.json|data\/modernmetals\/loot_table\/blocks\/[a-z]+_ore\.json)$/;
 for(const f of files(gen)){const name=path.relative(gen,f).replaceAll('\\','/');if(fs.existsSync(path.join(main,name)))assert(allowed.test(name),name);}
});
