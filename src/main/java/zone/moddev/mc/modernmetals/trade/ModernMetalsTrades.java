package zone.moddev.mc.modernmetals.trade;

import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.material.MaterialCatalogue;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.ForgeRegistries;

/** Adds the historical smith careers' Modern Metals offers after item registration. */
public final class ModernMetalsTrades {
    private static boolean registered;
    private ModernMetalsTrades() {}

    public static synchronized void register() {
        if (registered) return;
        VillagerProfession smith = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(
                new ResourceLocation("minecraft", "smith"));
        if (smith == null) return;
        VillagerCareer armor = smith.getCareer(1);
        VillagerCareer weapons = smith.getCareer(2);
        VillagerCareer tools = smith.getCareer(3);

        for (MaterialDefinition material : MaterialCatalogue.ALL) {
            if (material.kind() == MaterialDefinition.Kind.RARE_ORE) continue;
            int value = (int) (material.hardness() + material.strength() + material.magic() + material.toolLevel());
            int cost = Math.max(1, (int) (0.2F * value));
            int level = Math.max(1, Math.min(4, (int) (0.1F * value)));
            EntityVillager.ITradeList ingot = selling(ModContent.item(material.name() + "_ingot").get(), 12, cost);
            armor.addTrade(level, ingot);
            weapons.addTrade(level, ingot);
            tools.addTrade(level, ingot);
            // Low Fantasy still allows the smiths’ ordinary metal trades.
            addSales(armor, level, cost + (int) (material.hardness() / 2.0D), material,
                    "helmet", "chestplate", "leggings", "boots");
            addSales(tools, level, cost, material, "pickaxe", "axe", "shovel", "hoe", "crackhammer");
            addSales(weapons, level, cost, material, "sword", "bow", "crossbow");
        }
        registered = true;
    }

    private static void addSales(VillagerCareer career, int level, int cost,
            MaterialDefinition material, String... forms) {
        for (String form : forms) {
            career.addTrade(level, selling(ModContent.item(material.name() + "_" + form).get(), 1, cost));
        }
    }

    private static EntityVillager.ITradeList selling(final Item item, final int count, final int emeralds) {
        return (merchant, recipes, random) -> {
            if (ModernMetalsConfig.VILLAGER_TRADES.get()
                    && (!ContentPolicy.active().lowFantasy() || item.getRegistryName().getPath().endsWith("_ingot"))) {
                recipes.add(new MerchantRecipe(
                        new ItemStack(Items.EMERALD, Math.max(1, emeralds)),
                        new ItemStack(item, count)));
            }
        };
    }
}
