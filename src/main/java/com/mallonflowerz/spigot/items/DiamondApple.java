package com.mallonflowerz.spigot.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.mallonflowerz.spigot.Plugin;

public class DiamondApple {

    private Plugin plugin;

    public DiamondApple(Plugin plugin) {
        this.plugin = plugin;
    }

    public static ItemStack craftDimondApple() {
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.BLUE + "Manzana de Diamante");
        meta.setCustomModelData(2);

        item.setItemMeta(meta);

        return item;
    }

    public Recipe registerRecipeDiamondApple() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "custom_diamond_apple"), craftDimondApple());
        recipe.shape("BBB", "BAB", "BBB"); // Definir la forma de la receta

        // Definir los materiales correspondientes a cada letra de la forma
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        recipe.setIngredient('B', Material.DIAMOND);

        return recipe;
    }

    public static boolean hasDiamondApple(ItemStack i) {
        if (i == null)
            return false;

        if (i.hasItemMeta()) {
            if (i.getItemMeta().getCustomModelData() == 2
                    && ChatColor.stripColor(i.getItemMeta().getDisplayName()).endsWith("Diamante")
                    && i.getType() == Material.GOLDEN_APPLE) {
                return true;
            }
        }
        return false;
    }

}
