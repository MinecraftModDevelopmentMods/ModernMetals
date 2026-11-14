const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const output = path.join(root, 'build', 'generated', 'recipe-data');
if (!output.startsWith(root + path.sep + 'build' + path.sep)) {
  throw new Error('Recipe output must be inside the project build directory');
}
fs.rmSync(output, { recursive: true, force: true });

function files(directory) {
  if (!fs.existsSync(directory)) return [];
  return fs.readdirSync(directory, { withFileTypes: true }).sort((a, b) => a.name.localeCompare(b.name))
    .flatMap(entry => entry.isDirectory() ? files(path.join(directory, entry.name))
      : entry.name.endsWith('.json') ? [path.join(directory, entry.name)] : []);
}

function write(relative, value) {
  const file = path.join(output, relative);
  fs.mkdirSync(path.dirname(file), { recursive: true });
  fs.writeFileSync(file, JSON.stringify(value, null, 2) + '\n', 'utf8');
}

const recipes = new Map();
for (const directory of ['src/main/resources', 'src/generated/resources']) {
  const base = path.join(root, directory, 'data', 'modernmetals', 'recipes');
  for (const file of files(base)) {
    recipes.set(path.relative(base, file).replace(/\\/g, '/').slice(0, -5),
      JSON.parse(fs.readFileSync(file, 'utf8')));
  }
}

function add(name, recipe) {
  if (recipes.has(name)) throw new Error('Supplemental recipe duplicates ' + name);
  recipes.set(name, recipe);
  write('data/modernmetals/recipes/' + name + '.json', recipe);
}

const catalogue = fs.readFileSync(path.join(root,
  'src/main/java/zone/moddev/mc/modernmetals/material/MaterialCatalogue.java'), 'utf8');
const materials = [...catalogue.matchAll(/new MaterialDefinition\("([a-z]+)", Kind\.([A-Z_]+)/g)]
  .map(match => ({ name: match[1], alloy: match[2] === 'ALLOY' || match[2] === 'SPECIAL' }));
if (materials.length !== 21) throw new Error('Expected the complete 21-material catalogue');

// Shared roots include ores and storage blocks contributed by other mods.
// Define our contributions even when Modern Metals is installed on its own.
for (const category of ['ores', 'storage_blocks']) {
  const entries = new Set();
  if (category === 'storage_blocks') {
    for (const material of materials) {
      const id = 'storage_blocks/' + material.name;
      write('data/forge/tags/blocks/' + id + '.json', {
        replace: false, values: ['modernmetals:' + material.name + '_block']
      });
      entries.add('#forge:' + id);
    }
  }
  for (const directory of ['src/main/resources', 'src/generated/resources']) {
    const base = path.join(root, directory, 'data/forge/tags/blocks', category);
    for (const file of files(base)) {
      entries.add('#forge:' + category + '/' + path.relative(base, file).replace(/\\/g, '/').slice(0, -5));
    }
  }
  write('data/forge/tags/blocks/' + category + '.json', { replace: false, values: [...entries].sort() });
}

const craftingByOutput = new Map();
for (const outer of recipes.values()) {
  const recipe = outer.recipe || outer;
  if (!recipe.type.startsWith('minecraft:crafting_')) continue;
  const id = recipe.result.item;
  if (!craftingByOutput.has(id)) craftingByOutput.set(id, []);
  craftingByOutput.get(id).push(recipe);
}

function materialUnits(material, form, seen = []) {
  const basic = { ingot: 9, nugget: 1, block: 81, powder: 9, smallpowder: 1 };
  if (Object.hasOwn(basic, form)) return basic[form];
  if (seen.includes(form)) return 0;
  const costs = (craftingByOutput.get('modernmetals:' + material + '_' + form) || [])
    .map(recipe => {
      const ingredients = recipe.pattern
        ? recipe.pattern.flatMap(row => [...row].filter(s => s !== ' ').map(s => recipe.key[s]))
        : recipe.ingredients;
      return ingredients.reduce((sum, ingredient) =>
        sum + ingredientUnits(material, ingredient, [...seen, form]), 0) / (recipe.result.count || 1);
    }).filter(cost => cost > 0);
  return costs.length ? Math.min(...costs) : 0;
}

function ingredientUnits(material, ingredient, seen) {
  if (Array.isArray(ingredient)) {
    return Math.min(...ingredient.map(value => ingredientUnits(material, value, seen)));
  }
  const id = ingredient.tag || ingredient.item;
  const tag = id.match(/^forge:([^/]+)\/([a-z]+)$/);
  const item = id.match(/^modernmetals:([a-z]+)_(.+)$/);
  const forms = { ingots: 'ingot', nuggets: 'nugget', storage_blocks: 'block', rods: 'rod',
    gears: 'gear', plates: 'plate' };
  if (tag && tag[2] === material && forms[tag[1]]) {
    return materialUnits(material, forms[tag[1]], seen);
  }
  return item && item[1] === material ? materialUnits(material, item[2], seen) : 0;
}

const recyclingForms = ['axe', 'boots', 'helmet', 'sword', 'shovel', 'pickaxe', 'hoe', 'leggings',
  'chestplate', 'crackhammer', 'shears', 'rod', 'gear', 'slab', 'button', 'lever', 'wall', 'pressure_plate',
  'door', 'trapdoor', 'horse_armor', 'fishing_rod', 'bars', 'plate', 'bow', 'crossbow', 'arrow', 'bolt'];
for (const material of materials) {
  const name = material.name;
  write('data/forge/tags/items/plates/' + name + '.json',
    { replace: false, values: ['modernmetals:' + name + '_plate'] });
  add('crushing/' + name + '_block', {
    type: 'modernmetals:crushing', ingredient: { tag: 'forge:storage_blocks/' + name },
    result: { item: 'modernmetals:' + name + '_powder', count: 9 }
  });
  for (const form of material.alloy ? ['smallpowder', 'smallblend'] : ['smallpowder']) {
    add('smelting/' + name + '_' + form, {
      type: 'minecraft:smelting', ingredient: { item: 'modernmetals:' + name + '_' + form },
      result: 'modernmetals:' + name + '_nugget', experience: 0.05, cookingtime: 200
    });
  }
  for (const form of recyclingForms) {
    let units = Math.floor(materialUnits(name, form));
    // Preserve the losses in the historical ranged-weapon recycling recipes.
    if (form === 'bow' || form === 'fishing_rod' || form === 'lever') units = Math.min(units, 9);
    if (form === 'arrow') units = Math.min(units, 1);
    if (form === 'bolt') units = Math.min(units, 2);
    if (!units) throw new Error('Cannot establish recycling cost for ' + name + '_' + form);
    let resultForm = units >= 9 ? 'ingot' : 'nugget';
    let count = units >= 9 ? Math.floor(units / 9) : units;
    if ((form === 'wall' || form === 'crackhammer') && units >= 81) {
      resultForm = 'block';
      count = Math.floor(units / 81);
    }
    add('smelting/' + name + '_' + form + '_recycling', {
      type: 'modernmetals:legacy_smelting', ingredient: { item: 'modernmetals:' + name + '_' + form },
      result: { item: 'modernmetals:' + name + '_' + resultForm, count },
      experience: 0.05, cookingtime: 200
    });
  }
  for (const form of ['helmet', 'chestplate', 'leggings', 'boots', 'shield']) {
    add(name + '_' + form + '_plate_repair', {
      type: 'modernmetals:plate_repair', target: 'modernmetals:' + name + '_' + form,
      plate: { tag: 'forge:plates/' + name }
    });
  }
}

write('data/forge/tags/items/plates.json',
  { replace: false, values: materials.map(material => '#forge:plates/' + material.name) });

const standalone = [{ type: 'forge:not', value: { type: 'forge:mod_loaded', modid: 'basemetals' } }];
for (const [form, item, count] of [['coal', 'minecraft:coal', 1], ['coal_block', 'minecraft:coal_block', 9]]) {
  add('crushing/' + form, { type: 'modernmetals:crushing', conditions: standalone,
    ingredient: { item }, result: { item: 'modernmetals:coal_powder', count } });
}
add('coal_block_dust', { type: 'modernmetals:content_crafting', conditions: standalone,
  recipe: { type: 'minecraft:crafting_shaped', pattern: ['xxx', 'xxx', 'xxx'],
    key: { x: { tag: 'forge:dusts/coal' } }, result: { item: 'minecraft:coal_block' },
    group: 'minecraft:coal_block' } });

// Optional alloy ingredients need valid, empty tags in a standalone installation.
for (const name of ['copper', 'steel', 'zinc', 'nickel']) {
  const relative = 'data/forge/tags/items/dusts/' + name + '.json';
  if (!['src/main/resources', 'src/generated/resources'].some(directory =>
    fs.existsSync(path.join(root, directory, relative)))) {
    write(relative, { replace: false, values: [] });
  }
}
write('data/forge/tags/items/blends/steel.json', { replace: false, values: [] });

const aliases = {
  aluminum: ['aluminium', 'bauxite'],
  aluminumbrass: ['aluminiumbrass', 'alubrass'],
  galvanizedsteel: ['galvinizedsteel'],
  tungsten: ['wolfram']
};
for (const file of files(path.join(root, 'src/generated/resources/data/forge/tags'))) {
  const material = path.basename(file, '.json');
  for (const alias of aliases[material] || []) {
    const relative = path.relative(path.join(root, 'src/generated/resources'), file).replace(/\\/g, '/');
    const tag = relative.split('/tags/')[1].replace(/^(items|blocks)\//, '').slice(0, -5);
    write(relative.replace('/' + material + '.json', '/' + alias + '.json'),
      { replace: false, values: ['#forge:' + tag] });
  }
}

let unlocks = 0;
for (const [name, outer] of recipes) {
  const recipe = outer.recipe || outer;
  const crafting = recipe.type.startsWith('minecraft:crafting_');
  const smelting = ['minecraft:smelting', 'modernmetals:legacy_smelting'].includes(recipe.type);
  if (!crafting && !smelting) continue;
  const criteria = { has_recipe: { trigger: 'minecraft:recipe_unlocked',
    conditions: { recipe: 'modernmetals:' + name } } };
  if (crafting && name !== 'coal_block_dust') {
    criteria.has_material = { trigger: 'minecraft:inventory_changed',
      conditions: { items: [{ tag: 'forge:ingots/' + name.split('_')[0] }] } };
  }
  const ingredients = recipe.key ? Object.values(recipe.key) : recipe.ingredients || [recipe.ingredient];
  const choices = ingredients.flat().filter(value => value.item || value.tag);
  const relevant = choices.filter(value => !['minecraft:stick', 'minecraft:string', 'minecraft:feather'].includes(value.item)
    && value.tag !== 'forge:rods/wooden');
  const unique = new Map((relevant.length ? relevant : choices).map(value => [JSON.stringify(value), value]));
  let ingredientIndex = 0;
  for (const value of unique.values()) {
    // Append new blend discovery without renumbering the earned dust criteria.
    const blend = name.endsWith('_blend_base') && value.tag?.startsWith('forge:blends/');
    const key = blend ? 'has_blend_' + value.tag.split('/')[1] : 'has_ingredient_' + (++ingredientIndex);
    criteria[key] = {
      trigger: 'minecraft:inventory_changed', conditions: { items: [value] }
    };
  }
  write('data/modernmetals/advancements/recipes/' + name + '.json', {
    parent: 'minecraft:recipes/root', criteria, requirements: [Object.keys(criteria)],
    rewards: { recipes: ['modernmetals:' + name] }
  });
  unlocks++;
}
console.log('Generated ' + recipes.size + ' recipes with ' + unlocks + ' recipe unlocks');
