package com.mallonflowerz.spigot.Listener.Block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.Listener.Player.PlayerEvents;

public class BlockEvents implements Listener {

    private Plugin plugin;
    private PlayerEvents bed;

    public BlockEvents(Plugin plugin) {
        this.plugin = plugin;
        this.bed = new PlayerEvents(plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Integer days = plugin.getDays();
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (days >= 6) {
            if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.IRON_ORE ||
                    block.getType() == Material.GOLD_ORE || block.getType() == Material.REDSTONE_ORE ||
                    block.getType() == Material.LAPIS_ORE || block.getType() == Material.EMERALD_ORE ||
                    block.getType() == Material.ANCIENT_DEBRIS) {
                player.damage(5);
            }
        }

        if (days >= 10) {
            if (player.getLocation().getWorld().getTime() < 13000 &&
                    itemsBlocked.contains(block.getType())) {
                event.setCancelled(true);
            } else {
                //if (days >= 14 && ){
                    player.damage(1);
                //}
            }
        }

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Integer days = plugin.getDays();
        Block block = (Block) event.getBlock();

        if (days >= 6) {
            if (block.getType() == Material.STRUCTURE_VOID) {
                event.setCancelled(true);
            }
        }

        if (days >= 12) {
            if (bed.bedTypes.contains(block.getType()) && new Random().nextInt(100) + 1 <= 30) {
                TNTPrimed tnt = (TNTPrimed) block.getWorld().spawn(block.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(0);
                tnt.setVisibleByDefault(false);
                tnt.setYield(10.0F);
            }
        }

    }

    private final List<Material> itemsBlocked = Arrays.asList(
            // Madera (log y planks)
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.ACACIA_PLANKS,
            Material.BIRCH_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.OAK_PLANKS,
            Material.SPRUCE_PLANKS,
            // Piedra
            Material.STONE,
            // Minerales (ores)
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.ANCIENT_DEBRIS,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE,
            // Madera del Nether (Nether Logs y Nether Planks)
            Material.CRIMSON_STEM,
            Material.WARPED_STEM,
            Material.CRIMSON_PLANKS,
            Material.WARPED_PLANKS);
}
