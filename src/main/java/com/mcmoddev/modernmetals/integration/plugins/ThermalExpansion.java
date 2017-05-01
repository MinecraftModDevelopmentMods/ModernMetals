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
		
		materials.put("aluminumbrass", Options.enableAluminumBrass);
		materials.put("cadmium", Options.enableCadmium);
		materials.put("chromium", Options.enableChromium);
		materials.put("galvanizedsteel", Options.enableGalvanizedSteel);
		materials.put("magnesium", Options.enableMagnesium);
		materials.put("manganese", Options.enableManganese);
		materials.put("nichrome", Options.enableNichrome);
		materials.put("osmium", Options.enableOsmium);
		materials.put("plutonium", Options.enablePlutonium);
		materials.put("rutile", Options.enableRutile);
		materials.put("stainlesssteel", Options.enableStainlessSteel);
		materials.put("tantalum", Options.enableTantalum);
		materials.put("titanium", Options.enableTitanium);
		materials.put("tungsten", Options.enableTungsten);
		materials.put("uranium", Options.enableUranium);
		materials.put("zirconium", Options.enableZirconium);

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
