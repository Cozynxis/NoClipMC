# NoClipMC

Minimal Fabric 1.21.11 NoClip toggle with configurable keybind.

- Default key: **V**
- Change in Minecraft: Options → Controls → Key Binds → NoClipMC
- Press once to toggle on, press again to toggle off.
- Flight and collision bypass without changing game mode.

## Build
Java 21, Gradle 9.2.1:
```sh
gradle build
```
JAR: `build/libs/NoClipMC-1.0.0.jar`

GitHub Actions also builds the JAR. Download it from **Actions → Build NoClipMC → latest successful run → Artifacts**.

**Note:** This is a client-side implementation intended for local/integrated worlds. Dedicated servers may reject movement through blocks without a server-side implementation.
