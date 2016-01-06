## RPG Engine test

This a simple RPG Engine skeleton for testing integration with the Tiled map editor. The goal of this environment is being able to, given a TMX file and adjointed tilesets, load a fully interactive scene, containing at least:

- Interactive elements (Inspect objects etc.)
- Warp points (Doors, etc.)
- Various lights
- Trigger zones
- Map changes (open/close doors, move walls) -> Interactive tiles
- Possibly more...

### Engine Details

The Engine is divided into components or sub-systems, each being in charge of maintaining a certain aspect of the game:

- *AssetsManager*: In charge of loading game assets.
- *EntityManager*: Game entities, both playable and non-playable.
- *FilterManager*: Box2D collision filters.
- *Renderer*: Maintains a valid SpriteBatch and Viewport.
- *RoomManager*: Manages game rooms, their interactions and whatnot.
- *UIManager*: Manages the user interface, and most importantly, dialog trees.

### Other

The project also includes various Utility classes, such as a simplified TrueType Font loader, a ShaderProgram loader from .vert and .frag files, a noise generator, which will be used for various efects (e.g. flickering lights), and more.