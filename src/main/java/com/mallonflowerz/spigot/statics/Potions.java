package com.mallonflowerz.spigot.statics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potions {
    
    public static final PotionEffectType SP = PotionEffectType.SPEED;
    public static final PotionEffectType FR = PotionEffectType.FIRE_RESISTANCE;
    public static final PotionEffectType HB = PotionEffectType.HEALTH_BOOST;
    public static final PotionEffectType IN = PotionEffectType.INVISIBILITY;
    public static final PotionEffectType JU = PotionEffectType.JUMP;
    public static final PotionEffectType DR = PotionEffectType.DAMAGE_RESISTANCE;
    public static final PotionEffectType RE = PotionEffectType.REGENERATION;
    public static final PotionEffectType SW = PotionEffectType.SLOW;
    public static final PotionEffectType GL = PotionEffectType.GLOWING;

    public static List<PotionEffect> potions() {
        List<PotionEffect> potions = new ArrayList<>();
        potions.add(
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
        potions.add(
                new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1, true, false));
        potions.add(
                new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true, false));
        return potions;
    }

}
