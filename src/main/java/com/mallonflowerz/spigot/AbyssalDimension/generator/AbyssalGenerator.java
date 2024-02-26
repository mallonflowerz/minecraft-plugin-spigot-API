package com.mallonflowerz.spigot.AbyssalDimension.generator;

import java.util.Random;
import java.util.SplittableRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.AbyssalDimension.WorldEditPortal;

@SuppressWarnings("deprecation")
public class AbyssalGenerator extends ChunkGenerator {

    private static final int HEIGHT = 100;
    private static final boolean SMALL_ISLANDS_ENABLED = true;

    @Override
    public ChunkData generateChunkData(final World world, final Random random, final int chunkX, final int chunkZ,
            final BiomeGrid biome) {
        SimplexOctaveGenerator lowGenerator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);

        ChunkData chunk = createChunkData(world);
        lowGenerator.setScale(0.02D);

        for (int X = 0; X < 16; X++)
            for (int Z = 0; Z < 16; Z++) {
                int noise = (int) (lowGenerator.noise(chunkX * 16 + X, chunkZ * 16 + Z,
                        0.5D, 0.5D) * 15);

                if (noise <= 0) {
                    if (Plugin.worldEditFound && SMALL_ISLANDS_ENABLED && X == 8 && Z == 8)
                        if (random.nextInt(20) == 0) {
                            final int finalX = X;
                            final int finalZ = Z;

                            Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    WorldEditPortal.generateIsland(world, chunkX * 16 + finalX, chunkZ * 16 + finalZ,
                                            HEIGHT, new SplittableRandom(random.nextLong()));
                                }
                            }, 20);
                        }
                    continue;
                }

                // 100000
                int chance = Plugin.getInstance().getConfig().getInt("Toggles.TheBeginning.YticGenerateChance");
                if (chance > 1000000 || chance < 1) {
                    chance = 100000;
                }
                if (Plugin.worldEditFound && random.nextInt(chance) == 0) {
                    final int finalX = X;
                    final int finalZ = Z;

                    Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            WorldEditPortal.generateYtic(world, chunkX * 16 + finalX, chunkZ * 16 + finalZ, HEIGHT);
                        }
                    }, 20);

                }

                for (int i = 0; i < noise / 3; i++)
                    chunk.setBlock(X, i + HEIGHT, Z, Material.PURPUR_BLOCK);

                for (int i = 0; i < noise; i++)
                    chunk.setBlock(X, HEIGHT - i - 1, Z, Material.PURPUR_BLOCK);
            }
        return chunk;
    }

}
