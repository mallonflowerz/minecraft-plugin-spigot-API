package com.mallonflowerz.spigot.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;

import com.mallonflowerz.spigot.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CustomLootTable implements LootTable {

    private Plugin plugin;
    private final List<ItemStack> items;

    public CustomLootTable(Plugin plugin) {
        this.plugin = plugin;
        // Aquí puedes definir los items y sus probabilidades en tu loot table
        items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND, 1));
        items.add(new ItemStack(Material.GOLD_INGOT, 3));
        items.add(new ItemStack(Material.EMERALD, 9));
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "custom_loot_table");
    }

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        List<ItemStack> loot = new ArrayList<>();

        // Aquí puedes agregar lógica para decidir qué items entran en el loot
        for (ItemStack item : items) {
            if (random.nextDouble() <= 0.8) { // 50% de probabilidad de agregar el item
                loot.add(item);
            }
        }

        return loot;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillInventory'");
    }
}
