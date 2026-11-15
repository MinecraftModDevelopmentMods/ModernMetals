package zone.moddev.mc.modernmetals.content;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import zone.moddev.mc.lib.MMDLib;
import zone.moddev.mc.lib.api.MaterialExtensions;
import zone.moddev.mc.lib.common.runtime.RuntimeContentRegistrar;
import zone.moddev.mc.lib.common.types.MMDMaterial;
import zone.moddev.mc.modernmetals.ModernMetals;

/**
 * Modern Metals' registered content. The materials are MMDLib definitions in
 * {@code mmdlib/material/}; MMDLib's runtime registrar creates their items,
 * blocks, projectiles, armor materials and molten fluids.
 */
public final class ModContent {
    private static final Map<String, MMDMaterial> MATERIALS = new LinkedHashMap<>();
    private static RuntimeContentRegistrar registrar;
    private static volatile Set<FluidType> moltenTypes;

    private ModContent() {}

    public static void register(IEventBus bus) {
        registrar = new RuntimeContentRegistrar(ModernMetals.MOD_ID, MaterialExtensions.GLOBAL, bus);
        // MMDLib loads definitions in deferred construction work; registries open after that.
        bus.addListener(NewRegistryEvent.class, event -> queueMaterials());
    }

    private static void queueMaterials() {
        // loadedMaterials() is sorted by ID, so materials queue in alphabetical order.
        MMDLib.loadedMaterials().forEach((id, result) -> {
            if (!id.getNamespace().equals(ModernMetals.MOD_ID)) return;
            registrar.queue(result);
            MATERIALS.put(id.getPath(), result.material());
        });
        if (MATERIALS.isEmpty()) {
            throw new IllegalStateException("MMDLib did not load any Modern Metals material definitions");
        }
    }

    public static RuntimeContentRegistrar registrar() {
        return registrar;
    }

    /** Modern Metals' materials by name, such as {@code aluminum}. */
    public static Map<String, MMDMaterial> materials() {
        return Collections.unmodifiableMap(MATERIALS);
    }

    /** The registered Modern Metals item with this path, such as {@code aluminum_ingot}. */
    public static Optional<Item> item(String path) {
        var holder = registrar.items().get(ResourceLocation.fromNamespaceAndPath(ModernMetals.MOD_ID, path));
        return holder == null ? Optional.empty() : Optional.of(holder.get());
    }

    /** The fluid types of every molten metal. Available once registration has finished. */
    public static Set<FluidType> moltenTypes() {
        Set<FluidType> types = moltenTypes;
        if (types == null) {
            types = registrar.fluidFamilies().stream()
                    .map(family -> family.type.get())
                    .collect(Collectors.toUnmodifiableSet());
            moltenTypes = types;
        }
        return types;
    }
}
