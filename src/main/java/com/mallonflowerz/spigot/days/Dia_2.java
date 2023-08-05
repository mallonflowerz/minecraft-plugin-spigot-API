package com.mallonflowerz.spigot.days;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.mallonflowerz.spigot.statics.Potions;

public class Dia_2 implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event){
        if (event.getEntityType() != EntityType.PLAYER){
            LivingEntity entity = event.getEntity();
            entity.addPotionEffects(Potions.potions());
        }
    }
}
