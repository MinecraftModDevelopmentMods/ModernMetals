const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const root=path.resolve(__dirname,'..'), output=path.join(root,'build/generated/port-data');
const json=name=>JSON.parse(fs.readFileSync(path.join(output,name),'utf8'));
test('Forge 31 block loot preserves ore, doors and double slabs',()=>{
 const ore=json('data/modernmetals/loot_tables/blocks/beryllium_ore.json');
 assert.equal(ore.pools[0].entries[0].name,'modernmetals:beryllium_ore');
 assert(JSON.stringify(json('data/modernmetals/loot_tables/blocks/titanium_door.json')).includes('lower'));
 const slab=JSON.stringify(json('data/modernmetals/loot_tables/blocks/aluminum_slab.json'));
 assert(slab.includes('double'));assert(slab.includes('set_count'));
 assert.equal(json('data/modernmetals/loot_tables/blocks/aluminum.json').pools.length,0);
});
test('1.15 blasting preserves the furnace processing ingredients and yields',()=>{
 for(const name of ['aluminum_ore','beryllium_powder','titanium_blend']) {
  const value=json('data/modernmetals/recipes/blasting/'+name+'.json');
  assert.equal(value.type,'modernmetals:legacy_blasting');assert.equal(value.result.count,1);
 }
});
test('the reviewed material mining levels survive the port',()=>{
 const source=fs.readFileSync(path.join(root,'src/main/java/zone/moddev/mc/modernmetals/material/MaterialDefinition.java'),'utf8');
 assert(source.includes('return toolHarvestLevel;'));assert(source.includes('return oreHarvestLevel;'));
});
test('native fluid attributes have their animated sprites',()=>{
 for(const name of ['molten_metal_still','molten_metal_flow'])for(const suffix of ['.png','.png.mcmeta'])
  assert(fs.existsSync(path.join(root,'src/main/resources/assets/modernmetals/textures/block',name+suffix)));
});

test('ore overlay uses the reviewed 0.05 pixel separation',()=>{
 const model=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/assets/modernmetals/models/block/ore_overlay.json'),'utf8'));
 assert.deepEqual(model.elements[1].from,[-0.05,-0.05,-0.05]);assert.deepEqual(model.elements[1].to,[16.05,16.05,16.05]);
});
