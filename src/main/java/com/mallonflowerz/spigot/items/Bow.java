package com.mallonflowerz.spigot.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Bow {

    public static ItemStack craftBow() {
        ItemStack item = new ItemStack(Material.BOW);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Bow X");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);

        item.setItemMeta(meta);

        return item;
    }
}
