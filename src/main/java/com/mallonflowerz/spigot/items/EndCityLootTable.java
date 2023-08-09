package com.mallonflowerz.spigot.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Mundos;

public class EndCityLootTable {

    private Plugin plugin;
    private List<String> probs;
    private List<Integer> randomLoc = new ArrayList<>();
    private List<Material> alreadyItem;
    private final Random random = new Random();

    public EndCityLootTable(Plugin plugin) {
        this.plugin = plugin;
        this.probs = new ArrayList<>();
        this.alreadyItem = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            this.randomLoc.add(Integer.valueOf(i));
        }

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

    public void populateChest(Chest chest) {
        World world = chest.getWorld();
        Inventory i = chest.getBlockInventory();
        if (!world.getName().equals(Mundos.WORLD_END))
            return;
        if (i.contains(Material.DIAMOND_PICKAXE))
            return;
        roll(chest);
    }

    private void generateChest(Chest chest) {
        Iterator<String> t = this.probs.iterator();
        while (t.hasNext()) {
            String[] split = String.valueOf(t.next()).split(";");
            Inventory inventory = chest.getBlockInventory();

            Collections.shuffle(this.randomLoc);

            int add = 0;
            if (this.random.nextInt(100) + 1 <= getChance(split) && !this.alreadyItem.contains(getMaterial(split))) {
                if (getMaterial(split) == Material.TOTEM_OF_UNDYING) {
                    inventory.setItem((Integer) this.randomLoc.get(add).intValue(), new ItemStack(getMaterial(split)));
                    return;
                }
                int amount = generateValue(getMin(split), getMax(split));
                ItemStack s = new ItemStack(getMaterial(split), amount);
                inventory.setItem((Integer) this.randomLoc.get(add).intValue(), s);

                try {
                    int x = amount + getMin(split) / 2;
                    ItemStack s2 = new ItemStack(s.getType(), x);

                    int r = this.random.nextInt(5) + 1;
                    int slot = (this.random.nextBoolean() ? -1 : 1) * r;

                    inventory.setItem((Integer) this.randomLoc.get(add + slot).intValue(), s2);
                } catch (Exception e) {
                }

                if (add++ >= inventory.getSize() - 1) {
                    break;
                }
                this.alreadyItem.add(s.getType());
            }
        }
    }

    private List<String> addLoot(List<String> list, Material material, Integer min, Integer max, Integer chance) {
        list.add(String.format("%s;%s;%s;%s", material.toString(),
                min, max, chance));
        return list;
    }

    private void roll(Chest chest) {
        int rollTimes = this.random.nextInt(3) + 1;
        for (int i = 0; i < rollTimes; i++) {
            generateChest(chest);
        }
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
