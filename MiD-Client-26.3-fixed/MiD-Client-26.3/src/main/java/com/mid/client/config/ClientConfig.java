package com.mid.client.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Small, dependency-free configuration store for MiD Client. */
public final class ClientConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("midclient.properties");

    public static boolean toggleSprint = false;
    public static boolean hudEnabled = true;
    public static boolean showFps = true;
    public static boolean showCoordinates = true;
    public static boolean showKeystrokes = true;
    public static int hudAnchor = 0;

    private ClientConfig() {}

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }

        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(FILE)) {
            properties.load(input);
            toggleSprint = Boolean.parseBoolean(properties.getProperty("toggleSprint", "false"));
            hudEnabled = Boolean.parseBoolean(properties.getProperty("hudEnabled", "true"));
            showFps = Boolean.parseBoolean(properties.getProperty("showFps", "true"));
            showCoordinates = Boolean.parseBoolean(properties.getProperty("showCoordinates", "true"));
            showKeystrokes = Boolean.parseBoolean(properties.getProperty("showKeystrokes", "true"));
            hudAnchor = clamp(Integer.parseInt(properties.getProperty("hudAnchor", "0")), 0, 3);
        } catch (IOException | NumberFormatException exception) {
            System.err.println("[MiD Client] Could not load config: " + exception.getMessage());
        }
    }

    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("toggleSprint", Boolean.toString(toggleSprint));
        properties.setProperty("hudEnabled", Boolean.toString(hudEnabled));
        properties.setProperty("showFps", Boolean.toString(showFps));
        properties.setProperty("showCoordinates", Boolean.toString(showCoordinates));
        properties.setProperty("showKeystrokes", Boolean.toString(showKeystrokes));
        properties.setProperty("hudAnchor", Integer.toString(clamp(hudAnchor, 0, 3)));

        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream output = Files.newOutputStream(FILE)) {
                properties.store(output, "MiD Client settings - Minecraft 26.3");
            }
        } catch (IOException exception) {
            System.err.println("[MiD Client] Could not save config: " + exception.getMessage());
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
