package zone.moddev.mc.modernmetals.trade;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import zone.moddev.mc.lib.common.types.MMDMaterial;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;
import zone.moddev.mc.modernmetals.content.ModContent;

/** Smiths sell Modern Metals ingots and, where the Fantasy Level allows it, equipment. */
public final class ModernMetalsTrades {
    private static final List<String> ARMOR = List.of("helmet", "chestplate", "leggings", "boots");
    private static final List<String> TOOLS = List.of("pickaxe", "axe", "shovel", "hoe", "crackhammer");
    private static final List<String> WEAPONS = List.of("sword", "bow", "crossbow");

    private ModernMetalsTrades() {}

    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (!ModernMetalsConfig.VILLAGER_TRADES.get()) return;
        VillagerProfession profession = event.getType();
        List<String> equipment;
        if (profession == VillagerProfession.ARMORER) {
            equipment = ARMOR;
        } else if (profession == VillagerProfession.TOOLSMITH) {
            equipment = TOOLS;
        } else if (profession == VillagerProfession.WEAPONSMITH) {
            equipment = WEAPONS;
        } else {
            return;
        }

        ContentPolicy policy = ContentPolicy.active();
        ModContent.materials().forEach((name, material) -> {
            String type = material.properties().type();
            if (type.startsWith("rare")) return;
            double value = material.properties().hardness() + material.properties().strength()
                    + material.properties().magic() + toolLevel(material);
            int cost = Math.max(1, (int) (0.2D * value));
            int level = Math.max(1, Math.min(4, (int) (0.1D * value)));

            ModContent.item(name + "_ingot").ifPresent(ingot ->
                    event.getTrades().get(level).add(selling(ingot, 12, cost)));
            for (String form : equipment) {
                if (!policy.allows(name, form)) continue;
                Optional<Item> item = ModContent.item(name + "_" + form);
                if (item.isEmpty()) continue;
                event.getTrades().get(level).add(selling(item.get(), 1, cost + formPremium(form, material)));
                if (material.properties().magic() > 5.0D) {
                    event.getTrades().get(Math.min(5, level + 1)).add(enchanted(item.get(), cost + 7));
                }
            }
        });
    }

    private static int toolLevel(MMDMaterial material) {
        return Math.max(0, Math.min(4, (int) (material.properties().hardness() / 3.0D)));
    }

    private static int formPremium(String form, MMDMaterial material) {
        return ARMOR.contains(form) ? (int) (material.properties().hardness() / 2.0D) : 0;
    }

    private static ItemListing selling(Item item, int count, int emeralds) {
        return (trader, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, Math.min(64, Math.max(1, emeralds))),
                new ItemStack(item, count), 6, 5, 0.2F);
    }

    private static ItemListing enchanted(Item item, int baseCost) {
        return (trader, random) -> {
            int enchantmentLevel = 5 + random.nextInt(15);
            // Enchantments are data-driven; resolve them from the trader's world as vanilla does.
            var registries = trader.level().registryAccess();
            var enchantments = registries.registryOrThrow(Registries.ENCHANTMENT)
                    .getTag(EnchantmentTags.ON_TRADED_EQUIPMENT);
            ItemStack result = EnchantmentHelper.enchantItem(random, new ItemStack(item), enchantmentLevel,
                    registries, enchantments);
            return new MerchantOffer(new ItemCost(Items.EMERALD, Math.min(64, baseCost + enchantmentLevel)),
                    result, 3, 15, 0.2F);
        };
    }
}
