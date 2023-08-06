package com.mallonflowerz.spigot.creatures.dia_4;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spiders {

        private final static int M = Integer.MAX_VALUE;
        private Random random = new Random();

        public void applyRandomEffect(LivingEntity entity) {
                PotionEffectType[] effects = {
                                PotionEffectType.SPEED,
                                PotionEffectType.REGENERATION,
                                PotionEffectType.DAMAGE_RESISTANCE,
                                PotionEffectType.GLOWING,
                                PotionEffectType.INVISIBILITY,
                                PotionEffectType.JUMP,
                                PotionEffectType.HEALTH_BOOST,
                                PotionEffectType.INCREASE_DAMAGE
                };

                int maxEffects = 4; // Máximo número de efectos que pueden aplicarse
                int numEffects = random.nextInt(maxEffects) + 1; // Número aleatorio de efectos a aplicar

                for (int i = 0; i < numEffects; i++) {
                        PotionEffectType effectType = effects[random.nextInt(effects.length)];
                        int effectDuration = M;
                        int effectLevel = random.nextInt(2) + 1; // Nivel entre 1 y 4

                        PotionEffect potionEffect = new PotionEffect(effectType, effectDuration, effectLevel);
                        entity.addPotionEffect(potionEffect);
                }
        }
}
