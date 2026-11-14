package zone.moddev.mc.modernmetals.material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import zone.moddev.mc.modernmetals.material.MaterialDefinition.Kind;

/** Legacy physical statistics with independently reviewed 1.13.2 mining levels. */
public final class MaterialCatalogue {
    public static final List<MaterialDefinition> ALL = Collections.unmodifiableList(Arrays.asList(
            new MaterialDefinition("aluminum", Kind.ORE, 2.5d, 3.75d, 4.5d, 0xFFC5C8C1, false, 0, 0, 1),
            new MaterialDefinition("aluminumbrass", Kind.ALLOY, 1.0d, 7.5d, 4.5d, 0xFFEBAA56, false, 1, 1, 1),
            new MaterialDefinition("beryllium", Kind.ORE, 5.5d, 2.5d, 1.0d, 0xFFD5D3D5, false, 1, 1, 1),
            new MaterialDefinition("boron", Kind.ORE, 9.5d, 8d, 1.0d, 0xFF888888, false, 1, 2, 1),
            new MaterialDefinition("cadmium", Kind.RARE_ORE, 2d, 1d, 4.5d, 0xFFC9D4DA, false, 0, 0, 1),
            new MaterialDefinition("chromium", Kind.ORE, 9d, 3d, 4.5d, 0xFFCDCDCF, false, 1, 2, 1),
            new MaterialDefinition("galvanizedsteel", Kind.ALLOY, 5.5d, 15.25d, 4.5d, 0xFF9BA6A2, false, 2, 2, 1),
            new MaterialDefinition("iridium", Kind.RARE_ORE, 6.5d, 3d, 4.5d, 0xFFF8EDCC, false, 1, 2, 1),
            new MaterialDefinition("magnesium", Kind.ORE, 2.5d, 3.5d, 4.5d, 0xFF7F7F77, false, 0, 0, 1),
            new MaterialDefinition("manganese", Kind.ORE, 5d, 2.75d, 4.5d, 0xFFF5CFDA, false, 1, 1, 1),
            new MaterialDefinition("nichrome", Kind.ALLOY, 6d, 15.5d, 4.5d, 0xFFDEA054, false, 2, 2, 1),
            new MaterialDefinition("osmium", Kind.ORE, 7d, 2.75d, 4.5d, 0xFF7C8E99, false, 1, 2, 1),
            new MaterialDefinition("plutonium", Kind.RARE_ORE, 4d, 7.5d, 4.5d, 0xFFB333EA, false, 1, 1, 1),
            new MaterialDefinition("rutile", Kind.ORE, 6d, 0.25d, 4.5d, 0xFFBF928B, false, 1, 2, 1),
            new MaterialDefinition("stainlesssteel", Kind.ALLOY, 6d, 16d, 4.5d, 0xFFC5BFC1, false, 2, 2, 1),
            new MaterialDefinition("tantalum", Kind.ORE, 6.5d, 17.5d, 4.5d, 0xFFC4BEC2, false, 1, 1, 1),
            new MaterialDefinition("thorium", Kind.ORE, 3d, 1d, 1.0d, 0xFF4F5A5A, false, 1, 1, 1),
            new MaterialDefinition("titanium", Kind.SPECIAL, 6.5d, 16.25d, 4.5d, 0xFF73787E, false, 2, 2, 1),
            new MaterialDefinition("tungsten", Kind.ORE, 7.5d, 12.5d, 4.5d, 0xFF969696, false, 1, 2, 1),
            new MaterialDefinition("uranium", Kind.RARE_ORE, 6d, 16.25d, 4.5d, 0xFFA7B345, false, 1, 1, 1),
            new MaterialDefinition("zirconium", Kind.ORE, 5d, 3.5d, 4.5d, 0xFF929793, false, 1, 1, 1)));

    private MaterialCatalogue() {}
}
