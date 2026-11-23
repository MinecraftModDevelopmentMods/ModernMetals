const assert = require('node:assert/strict');
const test = require('node:test');
const fs = require('node:fs');
const path = require('node:path');

const root = path.resolve(__dirname, '..');
const generated = path.join(root, 'build/generated/recipe-data');
function resource(relative) {
  for (const directory of [generated, path.join(root, 'src/generated/resources'), path.join(root, 'src/main/resources')]) {
    const file = path.join(directory, relative);
    if (fs.existsSync(file)) return JSON.parse(fs.readFileSync(file, 'utf8'));
  }
  throw new Error('Missing resource ' + relative);
}
function recipe(name) {
  return resource('data/modernmetals/recipes/' + name + '.json');
}

test('hammer ore roots include the complete material tags for other mods to extend', () => {
  const crushable = resource('data/modernmetals/tags/blocks/crackhammer_crushable.json');
  assert.ok(crushable.values.includes('#forge:ores'));
  assert.ok(crushable.values.includes('#forge:storage_blocks'));
  const ores = resource('data/forge/tags/blocks/ores.json');
  const blocks = resource('data/forge/tags/blocks/storage_blocks.json');
  assert.ok(ores.values.includes('#forge:ores/beryllium'));
  assert.ok(ores.values.includes('#forge:ores/chromium'));
  assert.ok(blocks.values.includes('#forge:storage_blocks/galvanizedsteel'));
  assert.equal(ores.replace, false);
  assert.equal(blocks.replace, false);
});

test('beryllium dust discovers processing and its furnace recipe', () => {
  for (const name of ['beryllium_smallpowder', 'smelting/beryllium_powder']) {
    const advancement = resource('data/modernmetals/advancements/recipes/' + name + '.json');
    assert.deepEqual(advancement.rewards.recipes, ['modernmetals:' + name]);
    assert.ok(advancement.criteria.has_ingredient_1);
    assert.ok(advancement.requirements[0].includes('has_ingredient_1'));
  }
  assert.equal(recipe('smelting/beryllium_powder').result, 'modernmetals:beryllium_ingot');
});

test('coal processing has one owner and conserves the vanilla coal block quantity', () => {
  for (const name of ['coal_block_dust', 'crushing/coal', 'crushing/coal_block']) {
    assert.deepEqual(recipe(name).conditions,
      [{ type: 'forge:not', value: { type: 'forge:mod_loaded', modid: 'basemetals' } }]);
  }
  assert.deepEqual(recipe('coal_block_dust').recipe.pattern, ['xxx', 'xxx', 'xxx']);
  assert.equal(recipe('coal_block_dust').recipe.result.item, 'minecraft:coal_block');
  assert.equal(recipe('crushing/coal').result.count, 1);
  assert.equal(recipe('crushing/coal_block').result.count, 9);
});

test('the five alloy formulas keep their historical ingredients and yields', () => {
  for (const [name, tags, count] of [
    ['aluminumbrass', ['aluminum', 'aluminum', 'aluminum', 'copper'], 4],
    ['galvanizedsteel', ['steel', 'zinc'], 2],
    ['nichrome', ['nickel', 'chromium'], 2],
    ['stainlesssteel', ['steel', 'chromium'], 2],
    ['titanium', ['rutile', 'magnesium'], 1]
  ]) {
    const value = recipe(name + '_blend_base').recipe;
    assert.deepEqual(value.ingredients.map(ingredient => {
      const canonical = Array.isArray(ingredient) ? ingredient[0] : ingredient;
      return canonical.tag.replace('forge:dusts/', '');
    }), tags);
    assert.equal(value.result.count || 1, count);
    assert.equal(recipe('smelting/' + name + '_smallblend').result, 'modernmetals:' + name + '_nugget');
  }
});

test('steel blends can be used directly in downstream alloys without changing the yield', () => {
  for (const name of ['galvanizedsteel', 'stainlesssteel']) {
    const value = recipe(name + '_blend_base');
    assert.deepEqual(value.recipe.ingredients[0], [
      { tag: 'forge:dusts/steel' }, { tag: 'forge:blends/steel' }
    ]);
    assert.equal(value.recipe.result.count, 2);
    assert.deepEqual(value.conditions[0], {
      type: 'forge:or', values: [
        { type: 'modernmetals:non_empty_tag', tag: 'forge:dusts/steel' },
        { type: 'modernmetals:non_empty_tag', tag: 'forge:blends/steel' }
      ]
    });
    const unlock = resource('data/modernmetals/advancements/recipes/' + name + '_blend_base.json');
    assert.equal(unlock.criteria.has_blend_steel.conditions.items[0].tag, 'forge:blends/steel');
    assert.equal(unlock.criteria.has_ingredient_1.conditions.items[0].tag, 'forge:dusts/steel');
    assert.equal(unlock.criteria.has_ingredient_2.conditions.items[0].tag,
      name === 'stainlesssteel' ? 'forge:dusts/chromium' : 'forge:dusts/zinc');
  }
  assert.deepEqual(resource('data/forge/tags/items/blends/steel.json').values, []);
});

test('native recycling and repair quantities follow the registered crafting costs', () => {
  for (const [form, count] of [['plate', 1], ['gear', 1], ['door', 2]]) {
    const value = recipe('smelting/beryllium_' + form + '_recycling');
    assert.deepEqual(value.result, { item: 'modernmetals:beryllium_ingot', count });
  }
  assert.deepEqual(recipe('smelting/beryllium_rod_recycling').result,
    { item: 'modernmetals:beryllium_nugget', count: 4 });
  assert.deepEqual(recipe('beryllium_shield_plate_repair').plate, { tag: 'forge:plates/beryllium' });
  assert.deepEqual(resource('data/forge/tags/items/plates/beryllium.json').values, ['modernmetals:beryllium_plate']);
  assert.equal(fs.existsSync(path.join(generated,
    'data/modernmetals/advancements/recipes/beryllium_shield_plate_repair.json')), false);
});

test('metal buttons retain the two-nugget recycling recipe', () => {
  const materials = 'aluminum aluminumbrass beryllium boron cadmium chromium galvanizedsteel iridium magnesium manganese nichrome osmium plutonium rutile stainlesssteel tantalum thorium titanium tungsten uranium zirconium'.split(' ');
  for (const material of materials) {
    assert.deepEqual(recipe('smelting/' + material + '_button_recycling').result,
      { item: 'modernmetals:' + material + '_nugget', count: 2 });
  }
});

test('standalone optional alloy tags are declared without adding external materials', () => {
  for (const metal of ['copper', 'steel', 'zinc', 'nickel']) {
    assert.deepEqual(resource('data/forge/tags/items/dusts/' + metal + '.json').values, []);
  }
});
