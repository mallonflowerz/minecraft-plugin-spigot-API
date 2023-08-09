package com.mallonflowerz.spigot.creatures.dia_6;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.statics.Potions;

public class WitherSkeletons {

    public WitherSkeleton onSpawnWitherSkeleton(Location location) {
        WitherSkeleton witherSkeleton = (WitherSkeleton) location.getWorld().spawnEntity(location,
                EntityType.WITHER_SKELETON);
        EntityEquipment equip = witherSkeleton.getEquipment();
        witherSkeleton.setCustomName("Wither Skeleton The Golden");
        witherSkeleton.setCustomNameVisible(true);
        equip.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        equip.setHelmetDropChance(0F);
        equip.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        equip.setChestplateDropChance(0);
        equip.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        equip.setLeggingsDropChance(0);
        equip.setBoots(new ItemStack(Material.GOLDEN_BOOTS));
        equip.setBootsDropChance(0);

        witherSkeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true, false));
        witherSkeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 3, true, false));
        witherSkeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
        witherSkeleton.getEquipment().setItemInOffHand(new ItemStack(Material.GOLDEN_APPLE, 3));
        witherSkeleton.getEquipment().setItemInOffHandDropChance(1.0F);

        return witherSkeleton;
    }
}
