const fs = require('fs');

/*
 * settings section
 */

// mod-id of the mod we're generating recipes for
const modid = "modernmetals";
// all materials
const materials = [ 'Aluminum', 'AluminumBrass', 'Cadmium', 'Chromium', 'GalvanizedSteel', 'Iridium', 'Magnesium', 'Manganese', 'Nichrome', 'Osmium', 'Plutonium', 'Rutile', 'StainlessSteel', 'Tantalum', 'Titanium', 'Tungsten', 'Uranium', 'Zirconium' ];
// materials getting armors made
const armor_materials = [ 'Aluminum', 'AluminumBrass', 'Cadmium', 'Chromium', 'GalvanizedSteel', 'Iridium', 'Magnesium', 'Manganese', 'Nichrome', 'Osmium', 'Plutonium', 'Rutile', 'StainlessSteel', 'Tantalum', 'Titanium', 'Tungsten', 'Uranium', 'Zirconium' ];
// materials that are alloys
const alloy_materials = [ 'AluminumBrass', 'GalvanizedSteel', 'Nichrome', 'StainlessSteel', 'Titanium' ];
// alloy recipes
const alloy_recipes = { AluminumBrass: { recipe: { type: 'forge:ore_shapeless',
						   mix: [ { type: 'forge:ore_dict', ore: 'dustAluminum' },
							  { type: 'forge:ore_dict', ore: 'dustAluminum' },
							  { type: 'forge:ore_dict', ore: 'dustAluminum' },
							  { type: 'forge:ore_dict', ore: 'dustCopper' } ] },
					 output: 4,
					 config: { material: [ 'Aluminum', 'Copper' ] } },
			GalvanizedSteel: { recipe: { type: 'forge:ore_shapeless',
						     mix: [ { type: 'forge:ore_dict', ore: 'dustSteel' },
							    { type: 'forge:ore_dict', ore: 'dustNickel' } ] },
					   output: 2,
					   config: { material: [ 'Steel', 'Nickel' ] } },
			Nichrome: { recipe: { type: 'forge:ore_shapeless',
					      mix: [ { type: 'forge:ore_dict', ore: 'dustNickel' },
						     { type: 'forge:ore_dict', ore: 'dustChrome' } ] },
				    output: 2,
				    config: { material: [ 'Nickel', 'Chrome' ] } },
			StainlessSteel: { recipe: { type: 'forge:ore_shapeless',
						    mix: [ { type: 'forge:ore_dict', ore: 'dustSteel' },
							   { type: 'forge:ore_dict', ore: 'dustChrome' } ] },
					  output: 2,
					  config: { material: [ 'Steel', 'Chrome' ] } },
			Titanium: { recipe: { type: 'forge:ore_shapeless',
					      mix: [ { type: 'forge:ore_dict', ore: 'dustRutile' },
						     { type: 'forge:ore_dict', ore: 'dustMagnesium' } ] },
				    ouput: 2,
				    config: { material: [ 'Rutile', 'Magnesium' ] } } };
/*
 * True constants
 * What follows shouldn't need changing
 */
// named conditions
const conditions ={ armor: [ { type: 'basemetals:enabled',
			       optionName: 'thing',
			       optionValue: 'Armor' } ],
		    armor_repair: [ { type: 'basemetals:enabled',
				      optionName: 'thing',
				      optionValue: 'Plates' },
				    { type: 'basemetals:PlateRepairEnabled' } ],
		    bars: [ { type: 'basemetals:enabled',
			      optionName: 'thing',
			      optionValue: 'Bars' } ],
		    basics: [ { type: 'basemetals:enabled',
				optionName: 'thing',
				optionValue: 'Basics' } ],
		    basictools: [ { type: 'basemetals:enabled',
				    optionName: 'thing',
				    optionValue: 'BasicTools' } ],
		    button: [ { type: 'basemetals:enabled',
				optionName: 'thing',
				optionValue: 'Button' } ],
		    bowandarrow: [ { type: 'basemetals:enabled',
				     optionName: 'thing',
				     optionValue: 'BowAndArrow' } ],
		    crackhammer: [ { type: 'basemetals:hammer' } ],
		    crossbowandbolt: [ { type: 'basemetals:enabled',
					 optionName: 'thing',
					 optionValue: 'CrossbowAndBolt' } ],
		    door: [ { type: 'basemetals:enabled',
			      optionName: 'thing',
			      optionValue: 'Door' } ],
		    fishingrod: [  { type: 'basemetals:enabled',
				     optionName: 'thing',
				     optionValue: 'FishingRod' }],
		    gear: [ { type: 'basemetals:enabled',
			      optionName: 'thing',
			      optionValue: 'Gear' } ],
		    hammerenabled: [ { type: 'basemetals:hammer' } ],
		    horsearmor: [ { type: 'basemetals:enabled',
				    optionName: 'thing',
				    optionValue: 'HorseArmor' } ],
		    lever: [ { type: 'basemetals:enabled',
			       optionName: 'thing',
			       optionValue: 'Lever' } ],
		    material: [ { type: 'basemetals:enabled',
				  optionName: 'material',
				  optionValue: 'THIS' } ],
		    plate: [ { type: 'basemetals:enabled',
			       optionName: 'thing',
			       optionValue: 'Plate' } ],
		    pressureplate: [ { type: 'basemetals:enabled',
				       optionName: 'thing',
				       optionValue: 'PressurePlate' } ],
		    rod: [ { type: 'basemetals:enabled',
			     optionName: 'thing',
			     optionValue: 'Rod' } ],
		    shears: [ { type: 'basemetals:enabled',
				optionName: 'thing',
				optionValue: 'Shears' } ],
		    shields: [ { type: 'basemetals:enabled',
				 optionName: 'thing',
				 optionValue: 'Shields' } ],
		    slab: [ { type: 'basemetals:enabled',
			      optionName: 'thing',
			      optionValue: 'Slab' } ],
		    stairs: [ { type: 'basemetals:enabled',
				optionName: 'thing',
				optionValue: 'Stairs' } ],
		    trapdoor: [ { type:'basemetals:enabled',
				   optionName: 'thing',
				   optionValue: 'Trapdoor' } ],
		    wall: [ { type: 'basemetals:enabled',
			      optionName: 'thing',
			      optionValue: 'Wall' } ] };

// how conditions go together
const conditions_map = [ 'THIS_MATERIAL', 'ENABLED', 'MATERIALS' ];

// recipes all materials get
const base_recipes = [ 'nugget','ingot','block','powder','smallpowder','axe','pickaxe','shovel','hoe','sword',
                       'boots','helmet','chestplate','leggings','arrow','bars_1','bars_2','bow','button','bolt','crackhammer',
                       'crossbow','door','fishingrod','gear','horsearmor','lever','plate','pressureplate','rod','shears','shield','slab','stairs','trapdoor','wall' ];
// armor recipes
const armor_recipes = [ 'boots', 'chestplate', 'helmet', 'leggings', 'shield' ];
// recipes specifically for alloy materials
const alloy_recipes_extra = [ 'blend', 'smallblend' ];

// all main recipes
const patterns_basic = {
    // Basics
    nugget: { type: 'forge:ore_shapeless',
	      recipe: [ 'INGOT' ],
	      result: { material: 'NUGGET',
			count: 9 },
	      config: { enabled: [ 'Basics' ] },
	      "group": "basics" },
    ingot: { type: 'forge:ore_shaped',
	     recipe: [ "xxx", "xxx", "xxx" ],
	     key: { x: 'NUGGET' },
	     result: { material: 'INGOT',
		       count: 1 },
	     config: { enabled: [ 'Basics' ] },
	     group: "basics" },
    block: { type: 'forge:ore_shaped',
	     recipe: [ "xxx",
		       "xxx",
		       "xxx" ],
	     key : { x: 'INGOT' },
	     result: { material: 'BLOCK',
		       count: 1 },
	     config: { enabled: [ 'Basics' ] },
	     group: "basics" },
    powder: { type: 'forge:ore_shaped',
	      recipe: [ 'xxx',
			'xxx',
			'xxx' ],
	      key: { 'x': 'SMALLPOWDER' },
	      result: { material: 'POWDER',
			count: 1 },
	      config: { enabled:  [ 'Basics' ] },
	      group: "basics" },
    smallpowder: { type: 'forge:ore_shapeless',
		   recipe: [ 'POWDER' ],
		   result: { material: 'SMALLPOWDER',
			     count: 9 },
		   config: { enabled: [ 'Basics' ] },
		   group: "basics" },
    blend: { type: 'forge:ore_shaped',
	     recipe: [ 'xxx',
		       'xxx',
		       'xxx' ],
	     key: { x: 'SMALLBLEND' },
	     result: { material: 'BLEND',
		       count: 1 },
	     config: { enabled: [ 'Basics' ] },
	     group: "basics" },
    smallblend: { type: 'forge:ore_shapeless',
		  recipe: [ 'BLEND' ],
		  result: { material: 'SMALLBLEND',
			    count: 9 },
		  config: { enabled: [ 'Basics' ] },
		  group: "basics" },
    // Basic Tools
    axe: { type: 'forge:ore_shaped',
	   recipe: [ 'xx',
		     'xs',
		     ' s' ],
	   key: { x: 'INGOT',
		  s: "STICK" },
	   result: { material: 'AXE',
		     count: 1 },
	   config: { enabled: [ 'Basics', 'BasicTools' ] },
	   group: "tools" },
    pickaxe: { type: 'forge:ore_shaped',
	       recipe: [ 'xxx',
			 ' s ',
			 ' s ' ],
	       key: { x: 'INGOT',
		      s: "STICK" },
	       result: { material: 'PICKAXE',
			 count: 1 },
	       config: { enabled: [ 'Basics', 'BasicTools' ] },
	       group: "tools" },
    shovel: { type: 'forge:ore_shaped',
	      recipe: [ 'x',
			's',
			's' ],
	      key: { x: 'INGOT',
		     s: "STICK" },
	      result: { material: 'SHOVEL',
			count: 1 },
	      config: { enabled: [ 'Basics', 'BasicTools' ] },
	      group: "tools" },
    hoe: { type: 'forge:ore_shaped',
	   recipe: [ 'xx',
		     ' s',
		     ' s' ],
	   key: { x: 'INGOT',
		  s: "STICK" },
	   result: { material: 'HOE',
		     count: 1 },
	   config: { enabled: [ 'Basics', 'BasicTools' ] },
	   group: "tools" },
    sword: { type: 'forge:ore_shaped',
	     recipe: [ 'x',
		       'x',
		       's' ],
	     key: { x: 'INGOT',
		    s: "STICK" },
	     result: { material: 'SWORD',
		       count: 1 },
	     config: { enabled: [ 'Basics', 'BasicTools' ] },
	     group: "weapon" },
    // Armor
    boots: { type: 'forge:ore_shaped',
	     recipe: [ 'x x',
		       'x x' ],
	     key: { x: 'INGOT' },
	     result: { material: 'BOOTS',
		       count: 1 },
	     config: { enabled: [ 'Basics', 'Armor' ] },
	     group: "armor" },
    helmet: { type: 'forge:ore_shaped',
	      recipe: [ 'xxx',
			'x x' ],
	      key: { x: 'INGOT' },
	      result: { material: 'HELMET',
			count: 1 },
	      config: { enabled: [ 'Basics', 'Armor' ] },
	      group: "armor" },
    chestplate: { type: 'forge:ore_shaped',
		  recipe: [ 'x x',
			    'xxx',
			    'xxx' ],
		  key: { x: 'INGOT' },
		  result: { material: 'CHESTPLATE',
			    count: 1 },
		  config: { enabled: [ 'Basics', 'Armor' ] },
		  group: "armor" },
    leggings: { type: 'forge:ore_shaped',
		recipe: [ 'xxx',
			  'x x',
			  'x x' ],
		key: { x: 'INGOT' },
		result: { material: 'LEGGINGS',
			  count: 1 },
		config: { enabled: [ 'Basics', 'Armor' ] },
		group: "armor" },
    // Misc.
    arrow: { type: 'forge:ore_shaped',
	     recipe: [ 'x',
		       'y',
		       'z' ],
	     key: { x: 'NUGGET',
		    y: 'ROD',
		    z: 'FEATHER' },
	     result: { material: 'ARROW',
		       count: 4 },
	     config: { enabled: [ 'BowAndArrow', 'Basics' ] },
	     group: "weapon" },
    bars_1: { type: 'forge:ore_shaped',
	      recipe: [ 'xxx',
			'xxx' ],
	      key: { x: 'INGOT' },
	      result: { material: 'BARS', count: 16 },
	      config: { enabled: [ 'Bars', 'Basics' ] },
	      group: "misc" },
    bars_2: { type: 'forge:ore_shaped',
	      recipe: [ 'xxx' ],
	      key: { x: 'ROD' },
	      result: { material: 'BARS',
			count: 4 },
	      config: { enabled: [ 'Bars', 'Rod' ] },
	      group: "misc" },
    bow: { type: 'forge:ore_shaped',
	   recipe: [ ' xs',
		     'x s',
		     ' xs' ],
	   key: { x: 'ROD',
		  s: 'STRING' },
	   result: { material: 'BOW',
		     count: 1 },
	   config: { enabled: [ 'BowAndArrow', 'Rod' ] },
	   group: "weapon"  },    
    button: { type: 'forge:ore_shaped',
	      recipe: [ 'x',
			'x' ],
	      key: { x: 'NUGGET' },
	      result: { material: 'BUTTON',
			count: 1 },
	      config: { enabled: [ 'Button', 'Basics' ] },
	      group: "misc" },
    bolt: { type: 'forge:ore_shaped',
	    recipe: [ 'x',
		      'x',
		      'y' ],
	    key: { x: 'ROD',
		   y: 'FEATHER' },
	    result: { material: 'BOLT',
		      count: 4 },
	    config: { enabled: [ 'CrossbowAndBolt', 'Rod' ] },
	    group: "weapon"  },
    crackhammer: { type: 'forge:ore_shaped',
		   recipe: [ 'x',
			     'y',
			     'y' ],
		   key: { x: 'BLOCK',
			  y: 'STICK' },
		   result: { material: 'CRACKHAMMER',
			     count: 1 },
		   config: { enabled: [ 'Basics', 'hammerEnabled' ] },
		   group: "tools" },
    crossbow: { type: 'forge:ore_shaped',
		recipe: [ 'zxx',
			  ' yx',
			  'x z' ],
		key: { x: 'ROD',
		       y: 'STRING',
		       z: 'GEAR' },
		result: { material: 'CROSSBOW',
			  count: 1 },
		config: { enabled: [ 'CrossbowAndBolt', 'Rod', 'Gear' ] },
		group: "weapon"  },
    door: { type: 'forge:ore_shaped',
	    recipe: [ 'xx',
		      'xx',
		      'xx' ],
	    key: { x: 'INGOT' },
	    result: { material: 'DOOR',
		      count: 3 },
	    config: { enabled: [ 'Door', 'Basics' ] },
	    group: "misc" },
    fishingrod: { type: 'forge:ore_shaped',
		  recipe: [ "  x",
			    " xy",
			    "x y" ],
		  key: { x: 'ROD',
			 y: 'STRING' },
		  result: { material: 'FISHING_ROD',
			    count: 1 },
		  config: { enabled: [ 'FishingRod', 'Rod' ] },
		  group: "misc" },
    gear: { type: 'basemetals:variableOutputOre',
	    recipe: [" x ",
		     "xrx",
		     " x " ],
	    key: { x: 'INGOT',
		   r: 'ROD' },
	    result: { material: 'GEAR' },
	    config: { enabled: [ 'Gear', 'Basics' ] },
	    group: "misc",
	    config_key: "gear" },
    horsearmor: { type: 'forge:ore_shaped',
		  recipe: [ '  x',
			    'xyx',
			    'xxx' ],
		  key: { x: 'INGOT',
			 y: 'WOOL' },
		  result: { material: 'HORSE_ARMOR',
			    count: 1 },
		  config: { enabled: [ 'HorseArmor', 'Basics' ] },
		  group: "armor" },
    lever: { type: 'forge:ore_shaped',
	     recipe: [ 'x',
		       'y' ],
	     key: { x: 'ROD',
		    y: 'INGOT' },
	     result: { material: 'LEVER',
		       count: 1 },
	     config: { enabled: [ 'Lever', 'Rod', 'Basics' ] },
	     group: "misc" },
    plate: { type: 'basemetals:variableOutputOre',
	     recipe: [ 'xxx' ],
	     key: { x: 'INGOT' },
	     result: { material: 'PLATE' },
	     config: { enabled: [ 'Plate', 'Basics' ] },
	     group: "misc",
	     config_key:"plate" },
    pressureplate: { type: 'forge:ore_shaped',
		     recipe: [ 'xx' ],
		     key: { x: 'INGOT' },
		     result: { material: 'PRESSUREPLATE',
			       count: 1 },
		     config: { enabled: [ 'PressurePlate', 'Basics' ] },
		     group: "misc" },
    rod: { type: 'forge:ore_shaped',
	   recipe: [ 'x',
		     'x' ],
	   key : { x: 'INGOT' },
	   result: { material: 'ROD',
		     count: 4 },
	   config: { enabled: [ 'Rod', 'Basics' ] },
	   group: "misc" },
    shears: { type: 'forge:ore_shaped',
	      recipe: [ " x",
			"x " ],
	      key: { x: 'INGOT' },
	      result: { material: 'SHEARS', count: 1 },
	      config: { enabled: [ 'Shears', 'Basics' ] },
	      group: "tools" },
    shield: { type: 'forge:ore_shaped',
	      recipe: [ 'xyx',
			'xxx',
			' x ' ],
	      key: { x: 'INGOT', y: 'WOOD_PLANK' },
	      result: { material: 'SHIELD',
			count: 1 },
	      config: { enabled: [ 'Shields', 'Basics' ] },
	      group: "armor" },
    slab: { type: 'forge:ore_shaped',
	    recipe: [ 'xxx' ],
	    key : { x: 'BLOCK' },
	    result: { material: 'SLAB',
		      count: 6 },
	    config: { enabled: [ 'Slab', 'Basics' ] },
	    group: "misc" },
    stairs: { type: 'forge:ore_shaped',
	      recipe: [ 'x  ',
			'xx ',
			'xxx' ],
	      key : { x: 'BLOCK' },
	      result: { material: 'STAIRS',
			count: 4 },
	      config: { enabled: [ 'Stairs', 'Basics' ] },
	      group: "misc" },
    trapdoor: { type: 'forge:ore_shaped',
		recipe: [ 'xx',
			  'xx' ],
		key: { x: 'INGOT' },
		result: { material: 'TRAPDOOR',
			  count: 1 },
		config: { enabled: [ 'Trapdoor', 'Basics' ] },
		group: "misc" },
    wall: { type: 'forge:ore_shaped',
	    recipe: [ 'xxx',
		      'xxx' ],
	    key : { x: 'BLOCK' },
	    result: { material: 'WALL',
		      count: 6 },
	    config: { enabled: [ 'Wall', 'Basics' ] },
	    group: "misc" }
};

/*
 * functionality
 */

// no embedded caps except to start a name, usually
function toTitleCase(str)
{
    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}

// map a type-name descriptor to an 'Ingredient'
function mapNameIngredient( desc, material ) {
    let descLC = desc.toLowerCase();
    let materialLC = material.toLowerCase();
    let r_item = { type: "minecraft:item", item: "", data: 0 };
    let r_oredict = { type: "forge:ore_dict", ore: "" };
    
    switch( desc ) {
	// things known to be vanilla
    case 'STRING':
	r_item.item = "minecraft:string";
	return r_item;
    case 'STICK':
	r_oredict.ore = "stickWood";
	return r_oredict;
    case 'WOOL':
	r_item.item = "minecraft:wool";
	r_item.data = 32767; // wildcard - accept any meta
	return r_item;
    case 'WOOD_PLANK':
	r_oredict.ore = "plankWood";
	return r_oredict;
    case 'WOOD':
	r_oredict.ore = "logWood";
	return r_oredict;
    case 'FEATHER':
	r_item.item = "minecraft:feather";
	return r_item;
    default:
	r_oredict.ore = `${descLC}${toTitleCase(material)}`;
	return r_oredict;
    };
};

// map a type-name descriptor to a 'result' - can't do oredict
function mapNameResult( desc, material ) {
    let descLC = desc.toLowerCase();
    let materialLC = material.toLowerCase();
    switch( desc ) {
	// things known to be vanilla
    case 'STRING':
    case 'STICK':
    case 'FEATHER':
	return { type: "minecraft:item", item: `minecraft:${descLC}`, data: 0 };
    case 'WOOL':
	return { type: "minecraft:item", item: "minecraft:wool", data: 32767 };
    case 'WOOD_PLANK':
	return { type: "minecraft:item", item: "minecraft:planks", data: 32767 };
    case 'WOOD':
	return { type: "minecraft:item", item: "minecraft:log", data: 32767 };
    case 'PRESSUREPLATE': // pressure plates get special handling
	return { type: "minecraft:item", item: `${modid}:${materialLC}_pressure_plate`, data: 0 };
    default:
	return { type: "minecraft:item", item: `${modid}:${materialLC}_${descLC}`, data: 0 };
    }
}

// process the key for name mappings
function processRecipeKey( key, material ) {
    let ks = Object.keys(key);
    res = {};
    for( let i = 0; i < ks.length; i++ ) {
	let this_key = ks[i];
	res[this_key] = mapNameIngredient(key[this_key], material);
    }
    return res;
}

// process the recipe result into the expected form
function processRecipeResult( result, material ) {
    let res = mapNameResult( result.material, material );
    if( result.count > 1 ) {
	res.count = result.count;
    }
    return res;
}

// process and generate the conditions for the recipe
function processRecipeConditions( recipeName, conds, material ) {
    let res = { type: "forge:and", values: [] };
    let resValues = [];

    for( let i = 0; i < conditions_map.length; i++ ) {
	switch( conditions_map[i] ) {
	case 'THIS_MATERIAL':
	    resValues.push( { type: 'basemetals:enabled',
			      optionName: 'material',
			      optionValue: material } );
	    break;
	case 'ENABLED':
	    if( conds.hasOwnProperty( 'enabled' ) ) {
		let config = conds.enabled;
		for( let j = 0; j < config.length; j++ ) {
		    let name = config[j].toLowerCase();
		    let template = conditions[name];
		    for( let x = 0; x < template.length; x++ ) {
			let z = {};
			let m = template[x];
			let keylist = Object.keys(m);
			for( let k = 0; k < keylist.length; k++ ) {
			    let key = keylist[k];
			    z[key] = m[key];
			}
			resValues.push(z);
		    }
		}
	    }
	    break;
	case 'MATERIALS':
	    if( conds.hasOwnProperty('materials') ) {
		let p = conds.materials;
		for( let j = 0; j < p.length; j++ ) {
		    resValues.push( { type: 'basemetals:enabled',
				      optionName: 'material',
				      optionValue: p[j] } );
		}
	    }
	    break;
	}
    }
    res.values = resValues;
    return [ res ];
}

// process a shapeless recipes ingredient list
function processRecipeIngredients( ingredients, material ) {
    let res = [];
    for( let i = 0; i < ingredients.length; i++ ) {
	res.push( mapNameIngredient( ingredients[i], material ) );
    }
    return res;
}

// generate a standard recipe
function processRecipe( recipeName, material ) {
    let recipe = patterns_basic[recipeName];
    let res = { type: recipe.type, group: recipe.group, result: processRecipeResult( recipe.result, material ), conditions: processRecipeConditions( recipeName, recipe.config, material ) };
    
    switch( recipe.type ) {
    case "forge:ore_shaped":
	res.key = processRecipeKey( recipe.key, material );
	res.pattern = recipe.recipe;
	break;
    case "forge:ore_shapeless":
	res.ingredients = processRecipeIngredients( recipe.recipe, material );
	break;
    case "basemetals:variableOutputOre":
	res.key = processRecipeKey( recipe.key, material );
	res.pattern = recipe.recipe;
	res.config_key = recipe.config_key;
	break;
    }
    return res;
}

// generate an alloy recipe
function processAlloyConditions( depMaterials, material ) {
    let res = { type: "forge:and", values: [
	{ type: 'basemetals:enabled',
	  optionName: 'material',
	  optionValue: material } ] };

    for( let i = 0; i < depMaterials.length; i++ ) {
	res.values.push( { type: 'basemetals:enabled', optionName: 'material', optionValue: depMaterials[i] } );
    }
    res.values.push( { type: 'basemetals:enabled', optionName: 'thing', optionValue: 'Basics' } );
    return [ res ];
}

// process an alloy recipe
function processAlloyRecipe( recipe, material ) {
    // the alloy data has the type for future expansion, but is ignored for now
    let res = { type: "forge:ore_shapeless",
		ingredients: recipe.recipe.mix,
		conditions: processAlloyConditions( recipe.config.material, material ),
		result: mapNameResult( 'BLEND', material ),
		group: "alloys" };
    res.result.count = recipe.output;
    return res;
}

for( let i = 0; i < materials.length; i++ ) {
    for( let j = 0; j < base_recipes.length; j++ ) {
	let recipe = processRecipe( base_recipes[j], materials[i] );
	let fn = `output/${materials[i].toLowerCase()}_${base_recipes[j].toLowerCase()}.json`;
	fs.writeFileSync( fn, JSON.stringify( recipe, null, '\t' ) );
    }
}

for( let i = 0; i < armor_materials.length; i++ ) {
    for( let j = 0; j < armor_recipes.length; j++ ) {
	let recipe = processRecipe( armor_recipes[j], armor_materials[i] );
	let fn = `output/${armor_materials[i].toLowerCase()}_${armor_recipes[j].toLowerCase()}.json`;
	fs.writeFileSync( fn, JSON.stringify( recipe, null, '\t' ) );
    }
}

for( let i = 0; i < alloy_materials.length; i++ ) {
    let recipe = processAlloyRecipe( alloy_recipes[alloy_materials[i]], alloy_materials[i] );
    let b2sb = processRecipe( 'blend', alloy_materials[i] );
    let sb2b = processRecipe( 'smallblend', alloy_materials[i] );
    let fnbb = `output/${alloy_materials[i].toLowerCase()}_blend_base.json`;
    let fnb2sb = `output/${alloy_materials[i].toLowerCase()}_blend_smallblend.json`;
    let fnsb2b = `output/${alloy_materials[i].toLowerCase()}_smallblend_blend.json`;
    fs.writeFileSync( fnbb, JSON.stringify( recipe, null, '\t' ) );
    fs.writeFileSync( fnb2sb, JSON.stringify( b2sb, null, '\t' ) );
    fs.writeFileSync( fnsb2b, JSON.stringify( sb2b, null, '\t' ) );    
}

console.log('DONE!');
