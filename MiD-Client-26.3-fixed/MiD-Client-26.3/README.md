# MiD Client — Minecraft Java 26.3 / Fabric

A client-side utility mod with a ClickGUI and a small HUD editor. This project is deliberately limited to ordinary client-side convenience features; it does not include anti-cheat bypasses, packet manipulation, combat automation, or server-side exploits.

## Included in this first build

- **ClickGUI** — press **Right Shift** to open it.
- **HUD editor** — press **F8** or open it from ClickGUI.
- **HUD overlay** — enable/disable the overlay.
- **FPS counter** — optional FPS readout.
- **Coordinates** — optional block XYZ display.
- **Keystrokes** — optional WASD state display.
- **Toggle Sprint** — holds vanilla sprint state while moving forward, provided the player is not sneaking, riding, or fall-flying.
- **Persistent config** — settings are stored in `config/midclient.properties`.
- **Client-only metadata** — the mod is marked `environment: client` and has no server entrypoint.

## Toolchain

- Minecraft Java **26.3**
- Java **25** (JDK, not just a JRE)
- Fabric Loader **0.19.5**
- Fabric API **0.162.0+26.3**
- Fabric Loom **1.19-SNAPSHOT**
- Gradle **9.8.2**

Version recommendations are taken from the current Fabric development version guide. Minecraft 26.1+ uses the non-remapping Loom plugin ID `net.fabricmc.fabric-loom`; its 26.3 template declares Loader and Fabric API with Gradle’s `implementation` configuration (not `modImplementation`).

## Build

1. Install a JDK 25 and configure `JAVA_HOME` to point to it.
2. Install Gradle 9.8.2 (or a compatible newer 9.x release).
3. From this folder, run:

   ```bash
   gradle build
   ```

4. The mod jar is created under `build/libs/`, named `mid-client-1.0.0.jar`.
5. Install Fabric Loader 0.19.5 for Minecraft 26.3, then place the jar and Fabric API `0.162.0+26.3` in the instance's `mods` folder.

### Gradle wrapper note

`gradle/wrapper/gradle-wrapper.properties` is included, but the Gradle wrapper launcher JAR is not bundled in this archive. The build environment used to create this source archive only has Java 21 and cannot run the Minecraft 26.3 toolchain (Java 25); network access to fetch the wrapper binary was unavailable. After installing Gradle, you can generate the standard wrapper by running:

```bash
gradle wrapper --gradle-version 9.8.2
```

Then use `./gradlew build` (Windows: `gradlew.bat build`).

## Controls

| Key | Action |
|---|---|
| Right Shift | Open ClickGUI |
| F8 | Open HUD editor |

You can also change these keys in **Options → Controls → Key Binds → MiD Client**.

## Configuration

The config file is `config/midclient.properties`. You can edit it while the game is closed; the mod reads it on client startup.

## Compatibility and scope

This is a source-first initial implementation, not a guarantee of compatibility with every third-party client mod or server. HUD rendering uses Fabric's `HudElementRegistry`, and keybinds use Fabric's client key-mapping API. No anti-cheat system can be guaranteed to accept a mod on every server; server rules still apply.

## License

MIT. See [LICENSE](LICENSE).
