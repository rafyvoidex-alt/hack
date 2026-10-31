package com.mid.client;

import com.mid.client.config.ClientConfig;
import com.mid.client.gui.ClickGuiScreen;
import com.mid.client.gui.HudEditorScreen;
import com.mid.client.hud.MiDHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;

public final class MiDClient implements ClientModInitializer {
    public static final String MOD_ID = "midclient";

    private static KeyMapping openGuiKey;
    private static KeyMapping openHudEditorKey;

    @Override
    public void onInitializeClient() {
        ClientConfig.load();

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main")
        );

        openGuiKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.midclient.open_gui",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_RIGHT_SHIFT,
                category
        ));

        openHudEditorKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.midclient.open_hud_editor",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_F8,
                category
        ));

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(MOD_ID, "main_hud"),
                MiDHud::render
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()) {
                client.gui.setScreen(new ClickGuiScreen());
            }
            while (openHudEditorKey.consumeClick()) {
                client.gui.setScreen(new HudEditorScreen());
            }

            applyToggleSprint(client);
        });
    }

    private static void applyToggleSprint(Minecraft client) {
        if (client.player == null || !ClientConfig.toggleSprint) {
            return;
        }

        // This only automates the vanilla sprint input while moving forward.
        // It does not modify packets, movement speed, or server-side state.
        boolean shouldSprint = client.options.keyUp.isDown()
                && !client.options.keyShift.isDown()
                && !client.player.isFallFlying()
                && !client.player.isPassenger();
        if (shouldSprint) {
            client.player.setSprinting(true);
        }
    }
}
