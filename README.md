# BA Cursor Timer

A RuneLite plugin that shows how long the current Barbarian Assault wave has been running, right next to the mouse cursor, wherever the cursor is.

- **Accurate to the tick.** The timer counts game ticks rather than wall-clock time, so it moves in exact 0.6 second steps in lockstep with the server: `0.0`, `0.6`, `1.2`, … It shows seconds and tenths only and never rolls over into minutes (`9.6`, `10.2`, … `99.6`, `100.2`).
- **Same wave as the Barbarian Assault plugin.** The timer starts on the `---- Wave: N ----` game message and stops when you leave the wave (completing it, dying or exiting), on the same signals the built-in Barbarian Assault plugin uses for its "Wave N duration" chat message. It also stops on logout or world hop.
- **Follows the cursor.** It is drawn above interfaces, level with the cursor and to its right by default, and hidden while the cursor is outside the game window.

## Config

| Option | Default | Description |
| --- | --- | --- |
| Text color | white | Color of the wave timer |
| Horizontal offset | 16 | Pixels right of the cursor to draw the timer |
| Vertical offset | 0 | Pixels below the cursor to draw the timer |

## Development

Build and test with JDK 11:

```sh
./gradlew build
```

To launch a development client with the plugin loaded, run `BACursorTimerPluginTest.main` from `src/test`, or build `./gradlew shadowJar` and run `java -ea -jar build/libs/ba-cursor-timer-1.0-SNAPSHOT-all.jar --developer-mode`.
