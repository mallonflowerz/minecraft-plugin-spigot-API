package com.mallonflowerz.spigot.days;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.mallonflowerz.spigot.creatures.dia_4.Spiders;
import com.mallonflowerz.spigot.statics.Potions;

public class Dia_4 implements Listener {

    private final Spiders spiders = new Spiders();

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.SPIDER) {
            spiders.applyRandomEffect(event.getEntity());

        } else if (event.getEntityType() != EntityType.PLAYER && event.getEntityType() != EntityType.SPIDER) {
            LivingEntity entity = event.getEntity();
            entity.addPotionEffects(Potions.potions());
        }
    }
}
