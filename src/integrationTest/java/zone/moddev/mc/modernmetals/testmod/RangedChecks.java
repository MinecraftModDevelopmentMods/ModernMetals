package zone.moddev.mc.modernmetals.testmod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import zone.moddev.mc.modernmetals.content.MetalAmmoItem;
import zone.moddev.mc.modernmetals.content.MaterialBacked;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.content.RegistryHandle;
import zone.moddev.mc.modernmetals.entity.MaterialProjectile;
import zone.moddev.mc.modernmetals.entity.ModEntities;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;
import zone.moddev.mc.modernmetals.recipe.PlateRepairRecipe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import io.netty.buffer.Unpooled;

/** Target-native projectile and ammunition regression checks. */
final class RangedChecks {
    private int checks;
    private AbstractArrowEntity firedArrow;
    static int run(MinecraftServer server){
        RangedChecks probe=new RangedChecks();MinecraftForge.EVENT_BUS.register(probe);
        try {ServerWorld world=server.getWorld(DimensionType.OVERWORLD);probe.rangedWeapons(world);probe.crossbowAmmunition(world);}finally{MinecraftForge.EVENT_BUS.unregister(probe);}
        return probe.checks;
    }
    private void check(boolean value,String message){checks++;if(!value)throw new IllegalStateException(message);}
    private static ItemStack stack(String name){return new ItemStack(ModContent.item(name).get());}
    private static double damage(String item){return ((MaterialBacked)stack(item).getItem()).baseMetalsMaterial().baseAttackDamage();}
    private static boolean close(double a,double b){return Math.abs(a-b)<0.000001D;}
    private void rangedWeapons(ServerWorld world) {
        QuietPlayer player = new QuietPlayer(world);
        net.minecraft.util.math.BlockPos origin = world.getSpawnPoint().up(5);
        world.getChunk(origin);
        player.setPosition(origin.getX() + 0.5D, origin.getY(), origin.getZ() + 0.5D);
        int families = 0;
        for (Map.Entry<String, RegistryHandle<Item>> entry : ModContent.itemsById().entrySet()) {
            if (!entry.getKey().endsWith("_bow")) continue;
            families++;
            String materialName = entry.getKey().substring(0, entry.getKey().length() - 4);
            MaterialDefinition material = ((MaterialBacked) entry.getValue().get()).baseMetalsMaterial();
            ItemStack arrow = stack(materialName + "_arrow");
            ArrowItem arrowItem = (ArrowItem) arrow.getItem();
            AbstractArrowEntity projectile = arrowItem.createArrow(world, arrow, player);
            check(close(projectile.getDamage(), 1.0D + material.baseAttackDamage()),
                    "vanilla bow + " + materialName + " arrow");
            check(stack(materialName + "_fishing_rod").getMaxDamage() == material.toolDurability(),
                    "material fishing rod durability " + materialName);

            fire(world, player, stack(materialName + "_bow"), arrow,
                    2.0D * material.baseAttackDamage(), materialName + " bow");
            fire(world, player, stack(materialName + "_crossbow"), stack(materialName + "_bolt"),
                    2.0D * material.baseAttackDamage(), materialName + " crossbow");
            fire(world, player, stack(materialName + "_bow"), new ItemStack(Items.ARROW),
                    1.0D + material.baseAttackDamage(), materialName + " bow + vanilla arrow");
        }
        check(families == 21, "21 ranged material families");
        fire(world, player, new ItemStack(Items.BOW), new ItemStack(Items.ARROW), 2.0D, "vanilla shot");
        fire(world, player, stack("aluminum_bow"), stack("titanium_arrow"),
                damage("aluminum_bow") + damage("titanium_arrow"), "mixed bow materials");
        fire(world, player, stack("titanium_bow"), stack("aluminum_arrow"),
                damage("aluminum_bow") + damage("titanium_arrow"), "swapped bow materials");
        ItemStack enchanted = stack("galvanizedsteel_bow");
        enchanted.addEnchantment(Enchantments.POWER, 3);
        fire(world, player, enchanted, stack("aluminum_arrow"),
                damage("galvanizedsteel_bow") + damage("aluminum_arrow") + 2.0D, "Power after material damage");

        ItemStack named = stack("titanium_arrow");
        named.setDisplayName(new StringTextComponent("Recovered arrow"));
        named.getOrCreateTag().putString("ammo_proof", "preserved");
        MaterialProjectile original = (MaterialProjectile) ((ArrowItem) named.getItem())
                .createArrow(world, named, player);
        CompoundNBT saved = new CompoundNBT();
        ((Entity) original).writeWithoutTypeId(saved);
        MaterialProjectile restored = new MaterialProjectile(ModEntities.CUSTOM_ARROW.get(), world);
        ((Entity) restored).read(saved);
        check(ItemStack.areItemStacksEqual(named, restored.getAmmunition()), "arrow pickup NBT after reload");
        check(close(((AbstractArrowEntity) original).getDamage(), ((AbstractArrowEntity) restored).getDamage()),
                "arrow damage after reload");
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        try {
            original.writeSpawnData(buffer);
            restored.readSpawnData(buffer);
            check(ItemStack.areItemStacksEqual(named, restored.getAmmunition()), "arrow spawn NBT");
        } finally {
            buffer.release();
        }
    }

    private void fire(ServerWorld world, QuietPlayer player, ItemStack launcher,
            ItemStack ammunition, double expected, String label) {
        player.inventory.clear();
        ItemStack supply = ammunition.copy();
        supply.setCount(8);
        player.setHeldItem(Hand.MAIN_HAND, launcher);
        player.setHeldItem(Hand.OFF_HAND, supply);
        firedArrow = null;

        BowItem bow = (BowItem) launcher.getItem();
        bow.onPlayerStoppedUsing(launcher, world, player, bow.getUseDuration(launcher) - 20);
        check(firedArrow != null, label + " spawned on server");
        if (firedArrow != null) {
            check(close(firedArrow.getDamage(), expected), label + " damage expected=" + expected
                    + " actual=" + firedArrow.getDamage());
            check(firedArrow.getIsCritical(), label + " fully drawn critical");
        }
        check(supply.getCount() == 7, label + " consumes one round");
        check(launcher.getDamage() == 1, label + " uses one durability");
    }

    private void crossbowAmmunition(ServerWorld world) {
        QuietPlayer player = new QuietPlayer(world);
        net.minecraft.util.math.BlockPos origin = world.getSpawnPoint().up(5);
        world.getChunk(origin);
        player.setPosition(origin.getX() + 0.5D, origin.getY(), origin.getZ() + 0.5D);

        for (Map.Entry<String, RegistryHandle<Item>> entry : ModContent.itemsById().entrySet()) {
            if (!entry.getKey().endsWith("_crossbow")) continue;

            String name = entry.getKey();
            String boltName = name.substring(0, name.length() - "_crossbow".length()) + "_bolt";
            player.abilities.isCreativeMode = true;
            player.inventory.clear();
            ItemStack launcher = stack(name);
            player.setHeldItem(Hand.MAIN_HAND, launcher);

            release(world, player, launcher);
            checkBolt("aluminum_bolt", damage(name) + damage("aluminum_bolt"),
                    name + " Creative without ammunition");
            check(firedArrow != null
                    && firedArrow.pickupStatus == AbstractArrowEntity.PickupStatus.CREATIVE_ONLY,
                    name + " virtual bolt cannot be collected in Survival");
            check(launcher.getDamage() == 0, name + " Creative preserves durability");

            for (boolean creative : new boolean[] {false, true}) {
                player.abilities.isCreativeMode = creative;
                player.inventory.clear();
                launcher = stack(name);
                player.setHeldItem(Hand.MAIN_HAND, launcher);
                ItemStack supply = stack(boltName);
                supply.setCount(8);
                supply.setDisplayName(new StringTextComponent("Inventory bolt"));
                supply.getOrCreateTag().putString("ammo_proof", "inventory");
                player.inventory.setInventorySlotContents(9, supply);

                release(world, player, launcher);
                String label = name + " inventory bolt " + (creative ? "Creative" : "Survival");
                checkBolt(boltName, damage(name) + damage(boltName), label);
                if (firedArrow instanceof MaterialProjectile) {
                    ItemStack recovered = ((MaterialProjectile) firedArrow).getAmmunition();
                    check("inventory".equals(recovered.getOrCreateTag().getString("ammo_proof")),
                            label + " preserves ammunition NBT");
                }
                check(supply.getCount() == (creative ? 8 : 7), label + " ammunition use");
                check(launcher.getDamage() == (creative ? 0 : 1), label + " durability use");
            }
        }

        ItemStack[] wrongAmmunition = {new ItemStack(Items.ARROW), new ItemStack(Items.TIPPED_ARROW),
                new ItemStack(Items.SPECTRAL_ARROW), stack("titanium_arrow"), ItemStack.EMPTY};
        for (boolean creative : new boolean[] {false, true}) {
            for (ItemStack wrong : wrongAmmunition) {
                player.abilities.isCreativeMode = creative;
                player.inventory.clear();
                ItemStack launcher = stack("titanium_crossbow");
                ItemStack supply = wrong.copy();
                if (!supply.isEmpty()) supply.setCount(8);
                ItemStack original = supply.copy();
                player.setHeldItem(Hand.MAIN_HAND, launcher);
                player.setHeldItem(Hand.OFF_HAND, supply);

                release(world, player, launcher);
                String label = "wrong crossbow ammunition " + wrong + " "
                        + (creative ? "Creative" : "Survival");
                if (creative) {
                    checkBolt("aluminum_bolt", damage("titanium_crossbow") + damage("aluminum_bolt"), label);
                    check(firedArrow != null
                            && firedArrow.pickupStatus == AbstractArrowEntity.PickupStatus.CREATIVE_ONLY,
                            label + " virtual bolt pickup");
                } else {
                    check(firedArrow == null, label + " does not fire");
                }
                check(ItemStack.areItemStacksEqual(original, supply), label + " is not consumed");
                check(launcher.getDamage() == 0, label + " does not wear the launcher");
            }
        }

        // Bows still use Minecraft's virtual arrow when Creative players carry no ammunition.
        player.abilities.isCreativeMode = true;
        player.inventory.clear();
        ItemStack bow = stack("titanium_bow");
        player.setHeldItem(Hand.MAIN_HAND, bow);
        release(world, player, bow);
        check(firedArrow != null && !(firedArrow instanceof MaterialProjectile),
                "Creative bow keeps the vanilla arrow fallback");
        check(firedArrow != null && close(firedArrow.getDamage(), damage("titanium_bow") + 1.0D),
                "Creative bow fallback retains launcher damage");
    }

    private void release(ServerWorld world, QuietPlayer player, ItemStack launcher) {
        firedArrow = null;
        BowItem bow = (BowItem) launcher.getItem();
        bow.onPlayerStoppedUsing(launcher, world, player, bow.getUseDuration(launcher) - 20);
    }

    private void checkBolt(String ammunition, double expected, String label) {
        check(firedArrow instanceof MaterialProjectile, label + " creates a material projectile");
        if (!(firedArrow instanceof MaterialProjectile)) return;

        check(firedArrow.getType() == ModEntities.CUSTOM_BOLT.get(), label + " creates a bolt");
        check(((MaterialProjectile) firedArrow).getAmmunition().getItem() == stack(ammunition).getItem(),
                label + " keeps the correct ammunition");
        check(close(firedArrow.getDamage(), expected), label + " material damage");
        check(firedArrow.getIsCritical(), label + " fully drawn critical");
    }

    @SubscribeEvent
    public void arrowSpawned(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractArrowEntity) firedArrow = (AbstractArrowEntity) event.getEntity();
    }

    private static final class QuietPlayer extends FakePlayer {
        private QuietPlayer(ServerWorld world) {
            super(world, new GameProfile(UUID.fromString("00000000-0000-0000-0000-000000000131"),
                    "GameplayProbe"));
        }
        @Override protected void onNewPotionEffect(EffectInstance effect) {}
        @Override protected void onChangedPotionEffect(EffectInstance effect, boolean reapply) {}
        @Override protected void onFinishedPotionEffect(EffectInstance effect) {}
        private void submerge() { inWater = true; }
    }
}
