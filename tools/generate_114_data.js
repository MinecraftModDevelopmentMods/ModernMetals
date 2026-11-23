// Forge 28 moved block drops into data. Keep the established 1.13 item IDs and yields.
const fs=require('fs');
const path=require('path');
const root=path.resolve(__dirname,'..'),output=path.join(root,'build/generated/port-data');
if(!output.startsWith(path.join(root,'build')+path.sep))throw new Error('Unsafe output');
fs.rmSync(output,{recursive:true,force:true});
function write(name,value){const f=path.join(output,name);fs.mkdirSync(path.dirname(f),{recursive:true});fs.writeFileSync(f,JSON.stringify(value,null,2)+'\n');}
function files(dir){return fs.existsSync(dir)?fs.readdirSync(dir,{withFileTypes:true}).sort((a,b)=>a.name.localeCompare(b.name)).flatMap(e=>e.isDirectory()?files(path.join(dir,e.name)):e.name.endsWith('.json')?[path.join(dir,e.name)]:[]):[];}
const catalogue=fs.readFileSync(path.join(root,'src/main/java/zone/moddev/mc/modernmetals/material/MaterialCatalogue.java'),'utf8');
const materials=[...catalogue.matchAll(/new MaterialDefinition\("([a-z]+)", Kind\.([A-Z_]+)/g)].map(m=>({name:m[1],ore:m[2]!=='ALLOY',alloy:['ALLOY','SPECIAL'].includes(m[2])}));
if(materials.length!==21)throw new Error('Incomplete material catalogue');
const forms=['block','plate','bars','door','trapdoor','button','slab','lever','pressure_plate','stairs','wall'];
let loot=0,blasts=0;
for(const m of materials){
 for(const form of [...forms,...(m.ore||['galvanizedsteel','nichrome','stainlesssteel'].includes(m.name)?['ore']:[])]){
  const block='modernmetals:'+m.name+'_'+form;
  const entry={type:'minecraft:item',name:block};
  const conditions=[{condition:'minecraft:survives_explosion'}];
  if(form==='door')conditions.push({condition:'minecraft:block_state_property',block,properties:{half:'lower'}});
  if(form==='slab')entry.functions=[{function:'minecraft:set_count',count:2,conditions:[{condition:'minecraft:block_state_property',block,properties:{type:'double'}}]}];
  write('data/modernmetals/loot_tables/blocks/'+m.name+'_'+form+'.json',{type:'minecraft:block',pools:[{rolls:1,entries:[entry],conditions}]});loot++;
 }
 write('data/modernmetals/loot_tables/blocks/'+m.name+'.json',{type:'minecraft:block',pools:[]});loot++;
 for(const form of ['smallpowder',...(m.alloy?['smallblend']:[])]){
  write('data/modernmetals/recipes/blasting/'+m.name+'_'+form+'.json',{type:'modernmetals:legacy_blasting',ingredient:{item:'modernmetals:'+m.name+'_'+form},result:{item:'modernmetals:'+m.name+'_nugget',count:1},experience:0.05,cookingtime:100});blasts++;
 }
}
for(const directory of ['src/main/resources','src/generated/resources']){
 const base=path.join(root,directory,'data/modernmetals/recipes');
 for(const file of files(base)){
  const r=JSON.parse(fs.readFileSync(file,'utf8'));
  if(!['minecraft:smelting','modernmetals:legacy_smelting'].includes(r.type))continue;
  const relative=path.relative(base,file).replaceAll('\\','/');
  const result=typeof r.result==='string'?{item:r.result,count:1}:r.result;
  write('data/modernmetals/recipes/blasting/'+relative.replace(/^smelting\//,''),{...r,type:'modernmetals:legacy_blasting',result,cookingtime:Math.max(1,Math.floor((r.cookingtime||200)/2))});blasts++;
 }
}
console.log('Generated '+loot+' block loot tables and '+blasts+' blast-furnace recipes.');
