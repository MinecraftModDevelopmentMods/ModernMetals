const util = require('util'),
      fs = require('fs');

const mats = [ 'Aluminum', 'AluminumBrass', 'Cadmium', 'Chromium', 'GalvanizedSteel', 'Iridium', 'Magnesium', 'Manganese', 'Nichrome', 'Osmium', 'Plutonium', 'Rutile', 'StainlessSteel', 'Tantalum', 'Titanium', 'Tungsten', 'Uranium', 'Zirconium' ];

function toTitleCase(str)
{
    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}


function getBlockOreDict(str) {
    return { type: "forge:ore_dict", ore: `block${toTitleCase(str.toLowerCase())}` };
}

function getIngotName(str) {
    return { item: `modernmetals:${str.toLowerCase()}_ingot`, count: 9 };
}

function getConditions(str) {
    return { type: "forge:and",
	     values: [
		 { type: "modernmetals:enabled",
		   optionName: "material",
		   optionValue: str },
		 { type: "modernmetals:enabled",
		   optionName: "thing",
		   optionValue: "basics" } ] };
}

function getRecipeFor(str) {
    return { type: "forge:ore_shapeless",
	     ingredients: [ getBlockOreDict(str) ],
	     result: getIngotName(str),
	     group: "basics",
	     conditions: [ getConditions(str) ] };
}

for( let i = 0; i < mats.length; i++ ) {
    let f = `output/${mats[i].toLowerCase()}_block_ingot.json`;
    fs.writeFileSync( f, JSON.stringify( getRecipeFor(mats[i]), null, '\t' ) );
}
