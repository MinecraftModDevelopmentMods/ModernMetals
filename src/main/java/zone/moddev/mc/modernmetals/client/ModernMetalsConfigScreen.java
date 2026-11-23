package zone.moddev.mc.modernmetals.client;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.client.resources.I18n;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;

public final class ModernMetalsConfigScreen extends Screen {
    private static final String PREFIX = "config.modernmetals.";
    private final Screen parent;
    private final boolean inherited = ContentPolicy.inheritsBaseMetals();
    private String mode;

    public ModernMetalsConfigScreen(Screen parent) {
        super(new TranslationTextComponent(PREFIX + "title"));
        this.parent = parent;
        mode = inherited ? (ContentPolicy.active().lowFantasy() ? "low_fantasy" : "high_fantasy")
                : ModernMetalsConfig.CONTENT_MODE.get();
    }

    @Override
    protected void init() {
        int top = height / 2 - 25;
        Button selector = addButton(new Button(width / 2 - 125, top, 250, 20, label(), button -> {
            mode = "low_fantasy".equals(mode) ? "high_fantasy" : "low_fantasy";
            button.setMessage(label());
        }));
        selector.active = !inherited;
        addButton(new Button(width / 2 - 105, top + 52, 100, 20, I18n.format("gui.done"), button -> {
            if (!inherited && !mode.equals(ModernMetalsConfig.CONTENT_MODE.get())) ModernMetalsConfig.saveMode(mode);
            onClose();
        }));
        addButton(new Button(width / 2 + 5, top + 52, 100, 20, I18n.format("gui.cancel"), button -> onClose()));
    }

    private String label() {
        return I18n.format(PREFIX + "contentMode") + ": " + I18n.format(PREFIX + mode);
    }

    @Override public void onClose() { minecraft.displayGuiScreen(parent); }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        drawCenteredString(font, I18n.format(PREFIX + "title"), width / 2, 18, 0xFFFFFF);
        drawCenteredString(font, I18n.format(PREFIX + (inherited ? "inherited" : "restart")),
                width / 2, height / 2 + 8, 0xFFFFFF);
        super.render(mouseX, mouseY, partialTicks);
    }

}
