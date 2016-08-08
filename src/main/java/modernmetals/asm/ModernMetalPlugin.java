package modernmetals.asm;

import modernmetals.asm.transformer.EntityHorseTransformer;
import modernmetals.asm.transformer.HorseTypeTransformer;
import modernmetals.asm.transformer.ITransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("ModernMetals")
@IFMLLoadingPlugin.MCVersion("1.9")
@IFMLLoadingPlugin.SortingIndex(1001)
public class ModernMetalPlugin implements IFMLLoadingPlugin {
    static List<ITransformer> transformerList = new ArrayList<>();
    static boolean inDevelopment;

    public ModernMetalPlugin() {
        transformerList.add(new EntityHorseTransformer());
        transformerList.add(new HorseTypeTransformer());
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"modernmetals.asm.ModernMetalTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        inDevelopment = !(Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
