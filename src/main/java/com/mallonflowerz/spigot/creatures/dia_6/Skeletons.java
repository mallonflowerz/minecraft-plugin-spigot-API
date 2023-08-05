package com.mallonflowerz.spigot.creatures.dia_6;

import org.bukkit.Material;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.statics.Potions;

public class Skeletons {

    private final static int M = Integer.MAX_VALUE;

    public Skeleton spawnSkeletonOne(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 1");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 1, true, false));

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        return skeleton;
    }

    public Skeleton spawnSkeletonTwo(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 2");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 2, true, false));

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        return skeleton;
    }

    public Skeleton spawnSkeletonThree(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 3");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, M, 2, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 3, true, false));

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        return skeleton;
    }

    public Skeleton spawnSkeletonFour(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 4");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, M, 2, true, false));
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 4, true, false));

        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        return skeleton;
    }

}
