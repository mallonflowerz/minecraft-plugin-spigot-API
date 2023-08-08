package com.mallonflowerz.spigot.creatures.dia_10;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.statics.Potions;

public class SkeletonsLevelUp {

    private final static int M = Integer.MAX_VALUE;

    public Skeleton spawnSkeletonOne(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 1+");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(addEnchant(Material.IRON_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(addEnchant(Material.IRON_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(addEnchant(Material.IRON_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(addEnchant(Material.IRON_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 1, true, false));

        ItemStack bowItemStack = new ItemStack(Material.BOW);
        ItemMeta bow = skeleton.getEquipment().getItemInMainHand().getItemMeta();
        bow.addEnchant(Enchantment.ARROW_FIRE, 5, true);
        bowItemStack.setItemMeta(bow);
        skeleton.getEquipment().setItemInMainHand(bowItemStack);
        skeleton.getEquipment().setItemInMainHandDropChance(0);

        return skeleton;
    }

    public Skeleton spawnSkeletonTwo(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 2+");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(addEnchant(Material.DIAMOND_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(addEnchant(Material.DIAMOND_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(addEnchant(Material.DIAMOND_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(addEnchant(Material.DIAMOND_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 2, true, false));

        ItemStack bowItemStack = new ItemStack(Material.BOW);
        ItemMeta bow = skeleton.getEquipment().getItemInMainHand().getItemMeta();
        bow.addEnchant(Enchantment.ARROW_KNOCKBACK, 10, true);
        bowItemStack.setItemMeta(bow);
        skeleton.getEquipment().setItemInMainHand(bowItemStack);
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        return skeleton;
    }

    public Skeleton spawnSkeletonThree(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 3+");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(addEnchant(Material.DIAMOND_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(addEnchant(Material.DIAMOND_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(addEnchant(Material.DIAMOND_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(addEnchant(Material.DIAMOND_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, M, 2, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 3, true, false));

        ItemStack axeItemStack = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta axe = skeleton.getEquipment().getItemInMainHand().getItemMeta();
        axe.addEnchant(Enchantment.FIRE_ASPECT, 10, true);
        axeItemStack.setItemMeta(axe);
        skeleton.getEquipment().setItemInMainHand(axeItemStack);
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        return skeleton;
    }

    public Skeleton spawnSkeletonFour(CreatureSpawnEvent event) {
        Skeleton skeleton = (Skeleton) event.getEntity();
        skeleton.setCustomName("Skeleton Level 4+");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setHelmet(addEnchant(Material.NETHERITE_HELMET));
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplate(addEnchant(Material.NETHERITE_CHESTPLATE));
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggings(addEnchant(Material.NETHERITE_LEGGINGS));
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBoots(addEnchant(Material.NETHERITE_BOOTS));
        skeleton.getEquipment().setBootsDropChance(0);
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, M, 2, true, false));
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, M, 1, true, false));
        skeleton.addPotionEffect(new PotionEffect(Potions.HB, Integer.MAX_VALUE, 4, true, false));

        ItemStack swordItemStack = new ItemStack(Material.NETHERITE_SWORD);
        skeleton.getEquipment().setItemInMainHand(swordItemStack);
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        return skeleton;
    }

    private ItemStack addEnchant(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        item.setItemMeta(meta);
        return item;
    }

}
