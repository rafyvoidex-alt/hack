package com.mid.client.gui;

import com.mid.client.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ClickGuiScreen extends Screen {
    private static final int PANEL = 0xF0141722;
    private static final int PANEL_ALT = 0xFF1D2231;
    private static final int ACCENT = 0xFF9A82FF;
    private static final int TEXT = 0xFFF1F2F8;
    private static final int MUTED = 0xFF9CA3B5;

    public ClickGuiScreen() {
        super(Component.literal("MiD Client"));
    }

    @Override
    protected void init() {
        int panelWidth = Math.min(480, this.width - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = Math.max(14, (this.height - 300) / 2);
        int contentX = panelX + 178;
        int contentWidth = panelWidth - 202;

        this.addRenderableWidget(Button.builder(Component.literal("TOGGLE SPRINT"), button -> {
            ClientConfig.toggleSprint = !ClientConfig.toggleSprint;
            ClientConfig.save();
        }).bounds(contentX, panelY + 94, contentWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("HUD OVERLAY"), button -> {
            ClientConfig.hudEnabled = !ClientConfig.hudEnabled;
            ClientConfig.save();
        }).bounds(contentX, panelY + 124, contentWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("HUD EDITOR  →"), button ->
                Minecraft.getInstance().gui.setScreen(new HudEditorScreen())
        ).bounds(contentX, panelY + 164, contentWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("DONE"), button ->
                Minecraft.getInstance().gui.setScreen(null)
        ).bounds(contentX, panelY + 232, contentWidth, 22).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int panelWidth = Math.min(480, this.width - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = Math.max(14, (this.height - 300) / 2);

        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + 300, PANEL);
        graphics.fill(panelX, panelY, panelX + 4, panelY + 300, ACCENT);
        graphics.fill(panelX + 18, panelY + 58, panelX + 154, panelY + 282, PANEL_ALT);
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.text(this.font, "MiD CLIENT", panelX + 20, panelY + 18, TEXT, true);
        graphics.text(this.font, "26.3  /  FABRIC", panelX + 20, panelY + 35, MUTED, false);
        graphics.text(this.font, "UTILITY MODULES", panelX + 178, panelY + 65, ACCENT, true);

        graphics.text(this.font, "MOVEMENT", panelX + 30, panelY + 78, MUTED, true);
        graphics.text(this.font, "Toggle Sprint", panelX + 30, panelY + 101, TEXT, false);
        graphics.text(this.font, "VISUALS", panelX + 30, panelY + 145, MUTED, true);
        graphics.text(this.font, "HUD + stats", panelX + 30, panelY + 168, TEXT, false);
        graphics.text(this.font, "CONFIG", panelX + 30, panelY + 212, MUTED, true);
        graphics.text(this.font, "Saved locally", panelX + 30, panelY + 235, TEXT, false);

        graphics.text(this.font, "Sprint assist", panelX + 178, panelY + 88, TEXT, true);
        graphics.text(this.font, ClientConfig.toggleSprint ? "STATUS: ON" : "STATUS: OFF",
                panelX + 178, panelY + 118, ClientConfig.toggleSprint ? 0xFF79D6A5 : MUTED, true);
        graphics.text(this.font, "Overlay", panelX + 178, panelY + 148, TEXT, true);
        graphics.text(this.font, ClientConfig.hudEnabled ? "STATUS: ON" : "STATUS: OFF",
                panelX + 178, panelY + 178, ClientConfig.hudEnabled ? 0xFF79D6A5 : MUTED, true);
        graphics.text(this.font, "Right Shift: open menu", panelX + 178, panelY + 270, MUTED, false);
    }
}
