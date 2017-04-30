package com.mcmoddev.modernmetals.integration.plugins;

import java.util.HashMap;
import java.util.Map;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.item.ItemStack;

@MMDPlugin( addonId = ModernMetals.MODID, pluginId = ThermalExpansion.PLUGIN_MODID )
public class ThermalExpansion extends com.mcmoddev.lib.integration.plugins.ThermalExpansionBase implements IIntegration {

	public ThermalExpansion() {
		
	}
	
	public void init() {
		Map<String, Boolean> materials = new HashMap<>();
		
		materials.put("Aluminum", Options.enableAluminum);
		materials.put("AluminumBrass", Options.enableAluminumBrass);
		materials.put("Cadmium", Options.enableCadmium);
		materials.put("Chromium", Options.enableChromium);
		materials.put("GalvanizedSteel", Options.enableGalvanizedSteel);
		materials.put("Iridium", Options.enableIridium);
		materials.put("Magnesium", Options.enableMagnesium);
		materials.put("Manganese", Options.enableManganese);
		materials.put("Nichrome", Options.enableNichrome);
		materials.put("Osmium", Options.enableOsmium);
		materials.put("Plutonium", Options.enablePlutonium);
		materials.put("Rutile", Options.enableRutile);
		materials.put("StainlessSteel", Options.enableStainlessSteel);
		materials.put("Tantalum", Options.enableTantalum);
		materials.put("Titanium", Options.enableTitanium);
		materials.put("Tungsten", Options.enableTungsten);
		materials.put("Uranium", Options.enableUranium);
		materials.put("Zirconium", Options.enableZirconium);

		for( Map.Entry<String, Boolean> e : materials.entrySet()) {
			addFurnace(e.getValue(), e.getKey());
			addCrucible(e.getValue(), e.getKey());
			addPlatePress(e.getValue(), e.getKey());
			addPressStorage(e.getValue(), e.getKey());			
		}
		
		if( Options.enablePlutonium ) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName("plutonium").getFluid().getName(), 1000000);
		}
		
		if( Options.enableUranium ) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName("uranium").getFluid().getName(), 750000);
		}
		
		if( Options.enableChromium && Options.enableStainlessSteel ) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack( Materials.getMaterialByName("steel").getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName("chromium").getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName("stainlesssteel").getItem(Names.INGOT), 2));
		}
		
		if( Options.enableGalvanizedSteel ) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack( Materials.getMaterialByName("steel").getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName("zinc").getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName("galvanizedsteel").getItem(Names.INGOT), 2));
		}
		
		if( Options.enableRutile && Options.enableMagnesium && Options.enableTitanium ) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack( Materials.getMaterialByName("rutile").getItem(Names.INGOT), 1), new ItemStack( Materials.getMaterialByName("magnesium").getItem(Names.INGOT), 1), new ItemStack( Materials.getMaterialByName("titanium").getItem(Names.INGOT), 2));
		}
	}
}
