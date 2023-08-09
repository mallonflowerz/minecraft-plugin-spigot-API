package com.mallonflowerz.spigot.statics;

import org.bukkit.entity.Entity;

public class Mundos {

    public static final String WORLD_NETHER = "world_nether";
    public static final String WORLD_OVERWORLD = "world";
    public static final String WORLD_END = "world_the_end";

    public boolean isOverworld(Entity entity) {
        return entity.getLocation().getWorld().getName().equals(WORLD_OVERWORLD);
    }

    public boolean isNether(Entity entity) {
        return entity.getLocation().getWorld().getName().equals(WORLD_NETHER);
    }

    public boolean isEnd(Entity entity) {
        return entity.getLocation().getWorld().getName().equals(WORLD_END);
    }
}
