package com.mallonflowerz.spigot.creatures.dia_4;

import org.bukkit.entity.Spider;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import com.mallonflowerz.spigot.statics.Potions;

public class Spiders {

    private final static int M = Integer.MAX_VALUE;

    // Level 1: Speed 1, Regeneracion 2, HEALTH BOOST 2
    public Spider spiderLevelOne(CreatureSpawnEvent event) {
        Spider spider = (Spider) event.getEntity();
        spider.setCustomName("Spider Level 1");
        spider.setCustomNameVisible(false);
        spider.addPotionEffect(
                new PotionEffect(Potions.SP, M, 1, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.RE, M, 2, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.HB, M, 2, true, false));
        return spider;
    }

    // Level 2: Speed 2, Regeneracion 2, Resistencia 1
    public Spider spiderLevelTwo(CreatureSpawnEvent event) {
        Spider spider = (Spider) event.getEntity();
        spider.setCustomName("Spider Level 2");
        spider.setCustomNameVisible(false);
        spider.addPotionEffect(
                new PotionEffect(Potions.SP, M, 2, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.RE, M, 2, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.DR, M, 1, true, false));
        return spider;
    }

    // Level 3: Speed 3, Regeneracion 3, Resistencia 1, Jump 1
    public Spider spiderLevelThree(CreatureSpawnEvent event) {
        Spider spider = (Spider) event.getEntity();
        spider.setCustomName("Spider Level 3");
        spider.setCustomNameVisible(false);
        spider.addPotionEffect(
                new PotionEffect(Potions.SP, M, 3, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.RE, M, 3, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.DR, M, 1, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.JU, M, 1, true, false));
        return spider;
    }

    // Level 4: Speed 4, Regeneracion 1, Resistencia 2, Jump 2
    public Spider spiderLevelFour(CreatureSpawnEvent event) {
        Spider spider = (Spider) event.getEntity();
        spider.setCustomName("Spider Level 4");
        spider.setCustomNameVisible(false);
        spider.addPotionEffect(
                new PotionEffect(Potions.SP, M, 4, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.RE, M, 1, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.DR, M, 2, true, false));
        spider.addPotionEffect(
                new PotionEffect(Potions.JU, M, 2, true, false));
        return spider;
    }
}
