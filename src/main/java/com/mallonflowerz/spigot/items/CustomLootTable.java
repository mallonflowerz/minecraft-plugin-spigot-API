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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CustomLootTable implements LootTable {

    private Plugin plugin;
    private final List<String> probs;
    private Random random = new Random();

    public CustomLootTable(Plugin plugin) {
        this.plugin = plugin;
        // Aquí puedes definir los items y sus probabilidades en tu loot table
        probs = new ArrayList<>();

        addLoot(this.probs, Material.TOTEM_OF_UNDYING, 1, 2, 6);
        addLoot(this.probs, Material.ANCIENT_DEBRIS, 1, 4, 15);
        addLoot(this.probs, Material.DIAMOND, 1, 15, 30);
        addLoot(this.probs, Material.NETHERITE_INGOT, 1, 1, 2);
        addLoot(this.probs, Material.IRON_INGOT, 7, 40, 49);
        addLoot(this.probs, Material.ARROW, 7, 32, 65);
        addLoot(this.probs, Material.GOLD_BLOCK, 1, 5, 20);
        addLoot(this.probs, Material.GOLDEN_APPLE, 2, 11, 68);
        addLoot(this.probs, Material.DIAMOND_BLOCK, 1, 4, 30);
        addLoot(this.probs, Material.SHULKER_SHELL, 1, 2, 5);
        addLoot(this.probs, Material.GOLD_INGOT, 3, 25, 50);
        addLoot(this.probs, Material.FIREWORK_ROCKET, 6, 32, 60);
        addLoot(this.probs, Material.ENCHANTED_GOLDEN_APPLE, 1, 1, 3);
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "custom_loot_table");
    }

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        List<ItemStack> items = new ArrayList<>();

        for (String lootEntry : probs) {
            String[] entryParts = lootEntry.split(";");
            Material material = getMaterial(entryParts);
            int minAmount = getMin(entryParts);
            int maxAmount = getMax(entryParts);
            int chance = getChance(entryParts);

            if (random.nextInt(100) + 1 <= chance) {
                int amount = generateValue(minAmount, maxAmount);
                items.add(new ItemStack(material, amount));
            }
        }

        Collections.shuffle(items);
        return items;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillInventory'");
    }

    private List<String> addLoot(List<String> list, Material material, Integer min, Integer max, Integer chance) {
        list.add(String.format("%s;%s;%s;%s", material.toString(),
                min, max, chance));
        return list;
    }

    private int getMin(String[] s) {
        return Integer.valueOf(s[1]).intValue();
    }

    private int getMax(String[] s) {
        return Integer.valueOf(s[2]).intValue();
    }

    private int getChance(String[] s) {
        return Integer.valueOf(s[3]).intValue();
    }

    private Material getMaterial(String[] s) {
        return Material.valueOf(s[0]);
    }

    private int generateValue(int min, int max) {
        return this.random.nextInt(max - min) + this.random.nextInt(min) + 1;
    }
}
