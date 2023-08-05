package com.mallonflowerz.spigot.creatures.dia_6;

import org.bukkit.entity.Blaze;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import com.mallonflowerz.spigot.statics.Potions;

public class Blazes {

    public Blaze onSpawnBlaze(CreatureSpawnEvent event) {
        Blaze blaze = (Blaze) event.getEntity();
        blaze.addPotionEffect(
                new PotionEffect(Potions.DR, Integer.MAX_VALUE, 1));
        blaze.addPotionEffect(
                new PotionEffect(Potions.RE, Integer.MAX_VALUE, 2));
        blaze.addPotionEffect(
                new PotionEffect(Potions.HB, Integer.MAX_VALUE, 1));
        return blaze;
    }
}
