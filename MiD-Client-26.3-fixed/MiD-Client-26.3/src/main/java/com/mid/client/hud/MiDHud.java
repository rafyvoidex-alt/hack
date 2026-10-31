package com.mid.client.hud;

import com.mid.client.config.ClientConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;

public final class MiDHud {
    private static final int PANEL = 0xC9161926;
    private static final int ACCENT = 0xFF9A82FF;
    private static final int TEXT = 0xFFF2F3FA;
    private static final int MUTED = 0xFF9CA3B5;

    private MiDHud() {}

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui || !ClientConfig.hudEnabled) {
            return;
        }

        int width = 148;
        int lines = 1 + (ClientConfig.showFps ? 1 : 0)
                + (ClientConfig.showCoordinates ? 1 : 0)
                + (ClientConfig.showKeystrokes ? 1 : 0);
        int height = 12 + lines * 12;
        int x = switch (ClientConfig.hudAnchor) {
            case 1, 3 -> client.getWindow().getGuiScaledWidth() - width - 12;
            default -> 12;
        };
        int y = switch (ClientConfig.hudAnchor) {
            case 2, 3 -> client.getWindow().getGuiScaledHeight() - height - 12;
            default -> 12;
        };

        graphics.fill(x, y, x + width, y + height, PANEL);
        graphics.fill(x, y, x + 2, y + height, ACCENT);
        graphics.text(client.font, "MiD CLIENT", x + 9, y + 6, ACCENT, true);

        int lineY = y + 19;
        if (ClientConfig.showFps) {
            graphics.text(client.font, "FPS", x + 9, lineY, MUTED, false);
            graphics.text(client.font, Integer.toString(client.getFps()), x + 104, lineY, TEXT, true);
            lineY += 12;
        }
        if (ClientConfig.showCoordinates) {
            BlockPos pos = client.player.blockPosition();
            String coords = pos.getX() + "  " + pos.getY() + "  " + pos.getZ();
            graphics.text(client.font, "XYZ", x + 9, lineY, MUTED, false);
            graphics.text(client.font, coords, x + 34, lineY, TEXT, false);
            lineY += 12;
        }
        if (ClientConfig.showKeystrokes) {
            String keys = key(client.options.keyUp.isDown(), "W") + " "
                    + key(client.options.keyLeft.isDown(), "A") + " "
                    + key(client.options.keyDown.isDown(), "S") + " "
                    + key(client.options.keyRight.isDown(), "D");
            graphics.text(client.font, "KEYS", x + 9, lineY, MUTED, false);
            graphics.text(client.font, keys, x + 42, lineY, TEXT, true);
        }
    }

    private static String key(boolean pressed, String key) {
        return pressed ? "[" + key + "]" : key;
    }
}
