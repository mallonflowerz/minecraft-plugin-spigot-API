package com.mallonflowerz.spigot.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestTop {
    
    public void spawnChestWithItemsTop(Location location){

        Block block = location.getBlock();
        block.setType(Material.CHEST);

        BlockState state = block.getState();
        if (state instanceof Chest){
            Chest chest = (Chest) state;
            Inventory inventory = chest.getBlockInventory();

            inventory.addItem(new ItemStack(Material.STICK));
        }
    }
}
