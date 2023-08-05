package com.mallonflowerz.spigot.days;

import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.mallonflowerz.spigot.creatures.dia_4.Spiders;
import com.mallonflowerz.spigot.statics.Potions;

public class Dia_4 implements Listener {

    private final Spiders spiders = new Spiders();

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.SPIDER) {
            LivingEntity spider = event.getEntity();
            int randomNumber = new Random().nextInt(4);

            Spider spiderMethod = null;
            switch (randomNumber) {
                case 0:
                    spiderMethod = spiders.spiderLevelOne(event);
                    break;
                case 1:
                    spiderMethod = spiders.spiderLevelTwo(event);
                    break;
                case 2:
                    spiderMethod = spiders.spiderLevelThree(event);
                    break;
                case 3:
                    spiderMethod = spiders.spiderLevelFour(event);
                    break;
            }

            spider = spiderMethod;
        } else if (event.getEntityType() != EntityType.PLAYER && event.getEntityType() != EntityType.SPIDER){
            LivingEntity entity = event.getEntity();
            entity.addPotionEffects(Potions.potions());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player
                && (event.getDamager() instanceof Spider
                        && event.getDamager().getCustomName().equals("Spider Level 1"))) {
            event.setDamage(7);
        } else if (event.getEntity() instanceof Player
                && (event.getDamager() instanceof Spider
                        && event.getDamager().getCustomName().equals("Spider Level 2"))) {
            event.setDamage(9);
        } else if (event.getEntity() instanceof Player
                && (event.getDamager() instanceof Spider
                        && event.getDamager().getCustomName().equals("Spider Level 3"))) {
            event.setDamage(12);
        } else if (event.getEntity() instanceof Player
                && (event.getDamager() instanceof Spider
                        && event.getDamager().getCustomName().equals("Spider Level 4"))) {
            event.setDamage(15);
        }
    }
}
