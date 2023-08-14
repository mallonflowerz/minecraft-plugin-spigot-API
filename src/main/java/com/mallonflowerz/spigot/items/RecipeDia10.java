package com.mallonflowerz.spigot.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.mallonflowerz.spigot.Plugin;

public class RecipeDia10 {

    private final Plugin plugin;

    public RecipeDia10(Plugin plugin) {
        this.plugin = plugin;
    }

    public Recipe registerGoldenApplePlusRecipe() {
        ItemStack customItem = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.setDisplayName("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+");
        itemMeta.setCustomModelData(1);
        itemMeta.setLore(null);

        customItem.setItemMeta(itemMeta);

        // Golden Apple+
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_apple_plus"), customItem);
        recipe.shape("BBB", "BAB", "BBB"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('B', Material.DIAMOND);

        return recipe;
    }

    public Recipe registerGoldenApplePlusMaxRecipe() {
        ItemStack customItem = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.setDisplayName("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+ Max");
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(1);
        itemMeta.setLore(null);

        customItem.setItemMeta(itemMeta);

        // Golden Apple+
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_apple_plus_max"), customItem);
        recipe.shape("BBB", "BAB", "BBB"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('B', Material.DIAMOND_BLOCK);

        return recipe;
    }

    public Recipe registerShieldOpRecipe() {
        ItemStack customItem = new ItemStack(Material.SHIELD);
        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName("\u00A7lShield");
        itemMeta.setUnbreakable(true);
        itemMeta.setLore(null);

        customItem.setItemMeta(itemMeta);

        // Golden Apple+
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_shield"), customItem);
        recipe.shape("ABA", "AAA", "XAX"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.OBSIDIAN);
        recipe.setIngredient('B', Material.DIAMOND);

        return recipe;
    }
}
