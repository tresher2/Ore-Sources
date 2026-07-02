# My First Mod - Ore Sources
neoforge 1.21.1

Welcome to my first simple mod! It adds ore sources, currently in several types.

*This mod is in development!* This is essentially its alpha.
## short info:
- Ore sources are indestructible at stage 0.

- Ore sources store information about their growth stage, the day they were installed (within the remainder of 512, to save memory), and some have vanilla parameters like redstone's glow.

- Ore sources grow once per in-game day and night (20 real-world minutes, and their growth is not accelerated by a bed). It compares the installation day to the current day, allowing the block to grow even when the player isn't loading the chunk.

- The block compares the installation day to the current day at a random tick, so you may have to wait a bit when loading the chunk.

- It's compatible with the create mod, but not entirely well, since I have no idea how to make a block indestructible for drills at stage = 0. They don't call methods until the block is completely broken, or even getDestroyProgress. So I simply gave it tags that prevent drills from breaking it from there, since it's important to me that the block can be broken from creative mode or with a command.

- The mod is in development, and in the future, they plan to add custom mini-structures and somehow remove the spawning of all other ores from the world.

- The structures from the mod will have to spawn in specific biomes, like iron in plains, gold in deserts, etc.

- Yes, I'll remove useless items later, don't worry about that.
