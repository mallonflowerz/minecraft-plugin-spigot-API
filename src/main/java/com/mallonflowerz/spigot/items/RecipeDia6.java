package com.mallonflowerz.spigot.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;

public class RecipeDia6 {

    private final Plugin plugin;

    public RecipeDia6(Plugin plugin) {
        this.plugin = plugin;
    }

    public Recipe registerCustomRecipe() {
        ItemStack customItem = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.setDisplayName("\u00A7r\u00A76Space Helmet");
        itemMeta.setLore(null);

        customItem.setItemMeta(itemMeta);

        // Porro Antivicio
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_helmet"), customItem);
        recipe.shape("ABA", "XEX", "DCD"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.GOLD_BLOCK);
        recipe.setIngredient('C', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('E', Material.TOTEM_OF_UNDYING);

        return recipe;
    }

    public Recipe registerCustomBarrierCrafting() {
        ItemStack elCleanItem = new ItemStack(Material.BARRIER);
        ItemMeta elCleanMeta = elCleanItem.getItemMeta();

        elCleanMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        elCleanMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        elCleanMeta.setDisplayName("\u00A79El Clean");
        elCleanMeta.setLore(null);

        elCleanItem.setItemMeta(elCleanMeta);

        // El Clean
        ShapedRecipe elClean = new ShapedRecipe(new NamespacedKey(plugin, "custom_barrier"), elCleanItem);
        elClean.shape("AAA", "BCB", "AAA"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        elClean.setIngredient('A', Material.OBSIDIAN);
        elClean.setIngredient('B', Material.DIAMOND_BLOCK);
        elClean.setIngredient('C', Material.NETHERITE_INGOT);

        return elClean;
    }

    public ItemStack getCustomHelmetItem() {
        ItemStack customItem = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta itemMeta = customItem.getItemMeta();

        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.setDisplayName("\u00A7r\u00A76Space Helmet");
        itemMeta.setLore(null);

        customItem.setItemMeta(itemMeta);
        return customItem;
    }

    public ItemStack getCustomBarrierItem() {
        ItemStack elCleanItem = new ItemStack(Material.BARRIER);
        ItemMeta elCleanMeta = elCleanItem.getItemMeta();

        elCleanMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        elCleanMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        elCleanMeta.setDisplayName("\u00A79El Clean");
        elCleanMeta.setLore(null);

        elCleanItem.setItemMeta(elCleanMeta);

        return elCleanItem;
    }

    public void applyNegativeEffect(Player player) {
        if (this.plugin.getDays() >= 12) {
            player.damage(7);
        } else {
            player.damage(5);
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 0));
    }

    public void removeNegativeEffect(Player player) {
        player.removePotionEffect(PotionEffectType.POISON);
    }
}
