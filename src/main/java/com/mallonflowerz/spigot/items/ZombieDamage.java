package com.mallonflowerz.spigot.items;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieDamage {

    public Zombie z(Location l) {
        Zombie zombie = (Zombie) l.getWorld().spawnEntity(l,
                EntityType.ZOMBIE);
        zombie.setCustomName("ZombieAll");
        zombie.setCustomNameVisible(false);
        zombie.setSilent(true);

        zombie.setVisibleByDefault(false);

        zombie.addPotionEffect(
                new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
        return zombie;
    }
}
