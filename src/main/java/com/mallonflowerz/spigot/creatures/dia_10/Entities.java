package com.mallonflowerz.spigot.creatures.dia_10;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Witch;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.creatures.dia_6.WitherSkeletons;

public class Entities {

    private final WitherSkeletons witherSkeleton = new WitherSkeletons();

    public Ghast onGhast(Location location) {
        location.setX(location.getX() + 3);
        location.setY(location.getY() + 10);
        location.setZ(location.getZ() + 3);
        Ghast ghast = (Ghast) location.getWorld().spawnEntity(location, EntityType.GHAST);
        ghast.setCustomName("Ghast Ultravioleta");
        ghast.addPotionEffect(
                new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 4));
        ghast.addPotionEffect(
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 1));
        return ghast;
    }

    public Creeper onCreeper(Location location) {
        location.setX(location.getX() + 3);
        location.setY(location.getY() + 1);
        location.setZ(location.getZ() + 3);
        Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.setCustomName("Creeper Megalodon");
        creeper.addPotionEffect(
                new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 2));
        creeper.addPotionEffect(
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0));
        creeper.setExplosionRadius(25);
        creeper.setInvisible(true);
        creeper.setPowered(true);
        return creeper;
    }

    public TNTPrimed onTntPrimed(Location location) {
        TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks(100);
        tnt.setYield(20.0F);
        return tnt;
    }

    public Witch onWitch(Location location) {
        location.setX(location.getX() + 3);
        location.setY(location.getY() + 1);
        location.setZ(location.getZ() + 3);
        Witch witch = location.getWorld().spawn(location, Witch.class);
        witch.setCustomName("Bruja Instant");
        return witch;
    }

    public void onWitherSkeleton(Location location) {
        location.setX(location.getX() + 3);
        location.setY(location.getY() + 1);
        location.setZ(location.getZ() + 3);
        witherSkeleton.onSpawnWitherSkeleton(location);
    }
}
