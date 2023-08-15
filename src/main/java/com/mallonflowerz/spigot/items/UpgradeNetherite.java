package com.mallonflowerz.spigot.items;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.mallonflowerz.spigot.Plugin;

public class UpgradeNetherite {

    private Plugin plugin;

    public UpgradeNetherite(Plugin plugin) {
        this.plugin = plugin;
    }

    public static ItemStack craftSword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.DARK_RED + "Netherite Sword +");

        EquipmentSlot slot = EquipmentSlot.HAND;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 10.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 2.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack craftAxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.DARK_RED + "Netherite Axe +");

        EquipmentSlot slot = EquipmentSlot.HAND;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 11.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1.6D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack craftPickaxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.DARK_RED + "Netherite Pickaxe +");

        EquipmentSlot slot = EquipmentSlot.HAND;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 7.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1.4D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack craftShovel() {
        ItemStack item = new ItemStack(Material.NETHERITE_SHOVEL);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.DARK_RED + "Netherite Shovel +");

        EquipmentSlot slot = EquipmentSlot.HAND;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 7.5D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1.2D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack craftHoe() {
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.DARK_RED + "Netherite Hoe +");

        EquipmentSlot slot = EquipmentSlot.HAND;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 3.5D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 4.5D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        item.setItemMeta(meta);
        return item;
    }

    public Recipe registerAxe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_axe"), craftAxe());
        recipe.shape("AAA", "ABA", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_AXE);

        return recipe;
    }

    public Recipe registerPickaxe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_pickaxe"), craftPickaxe());
        recipe.shape("AAA", "ABA", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_PICKAXE);

        return recipe;
    }

    public Recipe registerSword() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_sword"), craftSword());
        recipe.shape("AAA", "ABA", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_SWORD);

        return recipe;
    }

    public Recipe registerHoe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_hoe"), craftHoe());
        recipe.shape("AAA", "ABA", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_HOE);

        return recipe;
    }

    public Recipe registerShovel() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_shovel"), craftShovel());
        recipe.shape("AAA", "ABA", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_SHOVEL);

        return recipe;
    }

    public static boolean hasTool(ItemStack i) {
        if (i == null)
            return false;

        if (i.hasItemMeta()) {
            if (i.getItemMeta().getCustomModelData() == 1
                    && ChatColor.stripColor(i.getItemMeta().getDisplayName()).endsWith("+")) {
                return true;
            }
        }
        return false;
    }
}
