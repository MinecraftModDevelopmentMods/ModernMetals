package zone.moddev.mc.modernmetals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import zone.moddev.mc.modernmetals.content.ContentPolicy;

final class ContentPolicyTests {
    @Test void oldAndInvalidSettingsPreserveHighFantasy() {
        for(String mode:new String[]{null,"","high_fantasy","unknown"})
            assertTrue(ContentPolicy.forMode(mode).allows("plutonium","pickaxe"));
    }
    @Test void lowFantasyRetainsIndustrialMaterialsAndRestrictsEquipment() {
        final ContentPolicy low=ContentPolicy.forMode(" LOW_FANTASY ");
        for(String name:new String[]{"plutonium","uranium","thorium","boron","beryllium","cadmium","rutile","chromium","magnesium","tungsten"}) {
            assertFalse(low.allows(name,"pickaxe"),name);
            assertFalse(low.allows(name,"helmet"),name);
            for(String form:new String[]{"ore","ingot","powder","fluid","plate","block"}) assertTrue(low.allows(name,form),name+":"+form);
        }
        for(String name:new String[]{"titanium","stainlesssteel","galvanizedsteel"}) {
            assertTrue(low.allows(name,"pickaxe"));assertTrue(low.allows(name,"helmet"));
            assertFalse(low.allows(name,"bow"));assertFalse(low.allows(name,"crossbow"));assertFalse(low.allows(name,"fishing_rod"));
        }
    }
    @Test void allModernMaterialsFollowTheEquipmentAndEngineeringMatrix() {
        final ContentPolicy low=ContentPolicy.forMode("low_fantasy");
        final java.util.Set<String> tools=new java.util.HashSet<>(java.util.Arrays.asList("galvanizedsteel","stainlesssteel","titanium"));
        final java.util.Set<String> protective=new java.util.HashSet<>(java.util.Arrays.asList("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium"));
        final java.util.Set<String> structural=new java.util.HashSet<>(java.util.Arrays.asList("aluminum","aluminumbrass","galvanizedsteel","nichrome","stainlesssteel","tantalum","titanium","tungsten","zirconium"));
        for(String name:new String[]{"aluminum","aluminumbrass","beryllium","boron","cadmium","chromium","galvanizedsteel","iridium","magnesium","manganese","nichrome","osmium","plutonium","rutile","stainlesssteel","tantalum","thorium","titanium","tungsten","uranium","zirconium"}) {
            for(String form:new String[]{"pickaxe","axe","sword","shovel","hoe","crackhammer","scythe","shears","arrow","bolt"})
                assertEquals(tools.contains(name),low.allows(name,form),name+":"+form);
            for(String form:new String[]{"helmet","chestplate","leggings","boots","horse_armor","shield","gear"})
                assertEquals(protective.contains(name),low.allows(name,form),name+":"+form);
            for(String form:new String[]{"door","trapdoor","bars","stairs","slab","pressure_plate","button","fence","fence_gate","wall","bookshelf","ladder","lever","tripwire_hook","flower_pot"})
                assertEquals(structural.contains(name),low.allows(name,form),name+":"+form);
            for(String form:new String[]{"bow","crossbow","fishing_rod"}) assertFalse(low.allows(name,form),name+":"+form);
        }
    }
}
