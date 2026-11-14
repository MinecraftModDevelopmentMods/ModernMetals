package zone.moddev.mc.modernmetals.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;

public final class ModernMetalsConfigScreen extends GuiScreen {
    private static final String PREFIX = "config.modernmetals.";
    private final GuiScreen parent;
    private final boolean inherited = ContentPolicy.inheritsBaseMetals();
    private String mode;

    public ModernMetalsConfigScreen(GuiScreen parent) {
        this.parent = parent;
        mode = inherited ? (ContentPolicy.active().lowFantasy() ? "low_fantasy" : "high_fantasy")
                : ModernMetalsConfig.CONTENT_MODE.get();
    }

    @Override
    protected void initGui() {
        int top = height / 2 - 25;
        GuiButton selector = addButton(new Button(20, width / 2 - 125, top, 250, label(), () -> {
            mode = "low_fantasy".equals(mode) ? "high_fantasy" : "low_fantasy";
            for (GuiButton button : buttons) {
                if (button.id == 20) button.displayString = label();
            }
        }));
        selector.enabled = !inherited;
        addButton(new Button(0, width / 2 - 105, top + 52, 100, I18n.format("gui.done"), () -> {
            if (!inherited && !mode.equals(ModernMetalsConfig.CONTENT_MODE.get())) {
                ModernMetalsConfig.saveMode(mode);
            }
            close();
        }));
        addButton(new Button(1, width / 2 + 5, top + 52, 100, I18n.format("gui.cancel"), this::close));
    }

    private String label() {
        return I18n.format(PREFIX + "contentMode") + ": " + I18n.format(PREFIX + mode);
    }

    @Override public void close() { mc.displayGuiScreen(parent); }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, I18n.format(PREFIX + "title"), width / 2, 18, 0xFFFFFF);
        drawCenteredString(fontRenderer, I18n.format(PREFIX + (inherited ? "inherited" : "restart")),
                width / 2, height / 2 + 8, 0xFFFFFF);
        super.render(mouseX, mouseY, partialTicks);
    }

    private static final class Button extends GuiButton {
        private final Runnable action;

        private Button(int id, int x, int y, int width, String text, Runnable action) {
            super(id, x, y, width, 20, text);
            this.action = action;
        }

        @Override public void onClick(double mouseX, double mouseY) { action.run(); }
    }
}
