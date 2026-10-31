package com.mid.client.gui;

import com.mid.client.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class HudEditorScreen extends Screen {
    private static final int PANEL = 0xF0141722;
    private static final int ACCENT = 0xFF9A82FF;
    private static final int TEXT = 0xFFF1F2F8;
    private static final int MUTED = 0xFF9CA3B5;

    public HudEditorScreen() {
        super(Component.literal("MiD HUD Editor"));
    }

    @Override
    protected void init() {
        int panelWidth = Math.min(430, this.width - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = Math.max(14, (this.height - 280) / 2);
        int buttonX = panelX + 220;
        int buttonWidth = panelWidth - 244;

        this.addRenderableWidget(Button.builder(Component.literal("FPS COUNTER"), button -> {
            ClientConfig.showFps = !ClientConfig.showFps;
            ClientConfig.save();
        }).bounds(buttonX, panelY + 86, buttonWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("COORDINATES"), button -> {
            ClientConfig.showCoordinates = !ClientConfig.showCoordinates;
            ClientConfig.save();
        }).bounds(buttonX, panelY + 116, buttonWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("KEYSTROKES"), button -> {
            ClientConfig.showKeystrokes = !ClientConfig.showKeystrokes;
            ClientConfig.save();
        }).bounds(buttonX, panelY + 146, buttonWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("MOVE HUD  ↻"), button -> {
            ClientConfig.hudAnchor = (ClientConfig.hudAnchor + 1) % 4;
            ClientConfig.save();
        }).bounds(buttonX, panelY + 190, buttonWidth, 22).build());

        this.addRenderableWidget(Button.builder(Component.literal("BACK"), button ->
                Minecraft.getInstance().gui.setScreen(new ClickGuiScreen())
        ).bounds(buttonX, panelY + 224, buttonWidth, 22).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int panelWidth = Math.min(430, this.width - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = Math.max(14, (this.height - 280) / 2);

        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + 280, PANEL);
        graphics.fill(panelX, panelY, panelX + 4, panelY + 280, ACCENT);
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.text(this.font, "HUD EDITOR", panelX + 22, panelY + 18, TEXT, true);
        graphics.text(this.font, "Choose what appears in your overlay.", panelX + 22, panelY + 38, MUTED, false);
        graphics.text(this.font, "FPS", panelX + 22, panelY + 92, TEXT, false);
        graphics.text(this.font, ClientConfig.showFps ? "ON" : "OFF", panelX + 170, panelY + 92,
                ClientConfig.showFps ? 0xFF79D6A5 : MUTED, true);
        graphics.text(this.font, "Coordinates", panelX + 22, panelY + 122, TEXT, false);
        graphics.text(this.font, ClientConfig.showCoordinates ? "ON" : "OFF", panelX + 170, panelY + 122,
                ClientConfig.showCoordinates ? 0xFF79D6A5 : MUTED, true);
        graphics.text(this.font, "Keystrokes", panelX + 22, panelY + 152, TEXT, false);
        graphics.text(this.font, ClientConfig.showKeystrokes ? "ON" : "OFF", panelX + 170, panelY + 152,
                ClientConfig.showKeystrokes ? 0xFF79D6A5 : MUTED, true);

        String[] anchors = {"Top left", "Top right", "Bottom left", "Bottom right"};
        graphics.text(this.font, "Position", panelX + 22, panelY + 196, TEXT, false);
        graphics.text(this.font, anchors[Math.max(0, Math.min(3, ClientConfig.hudAnchor))],
                panelX + 110, panelY + 196, ACCENT, true);
    }
}
