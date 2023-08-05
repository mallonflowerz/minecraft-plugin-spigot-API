package com.mallonflowerz.spigot.creatures.dia_6;

import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import com.mallonflowerz.spigot.statics.Potions;

public class Creepers {
    
    public Creeper onSpawnCreeper(CreatureSpawnEvent event){
        Creeper creeper = (Creeper) event.getEntity();
        creeper.setExplosionRadius(8);
        creeper.addPotionEffect(new PotionEffect(Potions.DR, Integer.MAX_VALUE, 1, true, false));
        creeper.addPotionEffect(new PotionEffect(Potions.SP, Integer.MAX_VALUE, 2, true, false));
        return creeper;
    }
}
