package com.mallonflowerz.spigot.Listener.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;

import net.md_5.bungee.api.ChatColor;

public class PlayerEvents implements Listener {

    private Plugin plugin;
    private final Random random = new Random();

    public PlayerEvents(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStepFire(PlayerMoveEvent event) {
        Integer days = plugin.getDays();
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        Location from = event.getFrom();
        Location to = event.getTo();

        if (days >= 8 && days < 10) {
            if (!player.isInsideVehicle()) {
                if (to != null && new Random().nextDouble() < 0.2 &&
                        (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ())) {
                    // player.setFireTicks(20);
                }
            } else if (player.isInsideVehicle() && player.getVehicle().getType() != EntityType.BOAT) {
                if (to != null && new Random().nextDouble() < 0.2 &&
                        (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ())) {
                    // player.setFireTicks(20);
                    // player.getVehicle().setFireTicks(20);
                }
            }
            if (to != null && block.getType() != Material.AIR && block.getType() == Material.SAND &&
                    (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ())) {
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Integer days = plugin.getDays();
        ItemStack dropped = event.getItemDrop().getItemStack();

        if (days >= 6) {
            if (dropped.getType() == Material.STRUCTURE_VOID) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getRightClicked().getType() == EntityType.VILLAGER) {
                Player player = event.getPlayer();
                if (random.nextInt(100) + 1 == 1) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.BAD_OMEN, 10, 3));
                }
            }
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Integer days = plugin.getDays();
        Block clickedBlock = event.getClickedBlock();

        if (days >= 10) {
            if (clickedBlock != null && bedTypes.contains(clickedBlock.getType())) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                        event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (random.nextInt(100) <= 30) {
                        TNTPrimed tnt = clickedBlock.getWorld().spawn(clickedBlock.getLocation(), TNTPrimed.class);
                        tnt.setFuseTicks(0);
                        tnt.setVisibleByDefault(false);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Integer days = plugin.getDays();
        Player player = event.getPlayer();
        Block bed = event.getBed();

        if (days >= 8) {
            if (player.getWorld().getTime() < 13000) {
                // No es de noche
                event.getPlayer().sendMessage(String
                        .format(ChatColor.translateAlternateColorCodes('&', "&l&4No puedes dormir ahora!")));
            } else {
                event.getPlayer().sendMessage(String
                        .format(ChatColor.translateAlternateColorCodes('&',
                                "&aHas restablecido el contador de Phantoms.")));
                event.getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 0);
            }
            bed.getLocation().getWorld()
                    .playSound(player.getBedSpawnLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
            bed.getLocation().getWorld()
                    .spawnParticle(Particle.EXPLOSION_HUGE, player.getBedSpawnLocation(), 1);
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Integer days = plugin.getDays();

        if (days >= 8) {
            if (event.getItem().getType().equals(Material.MILK_BUCKET)) {
                event.setCancelled(true);
            }
        }

        if (days >= 10) {
            if (event.getItem().getType() == Material.GOLDEN_APPLE &&
                    event.getItem().getItemMeta().getDisplayName()
                            .equals("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+")) {
                Player player = (Player) event.getPlayer();
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.HEALTH_BOOST, 24000, 1));
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 24000, 0));
            } else if (event.getItem().getType() == Material.GOLDEN_APPLE &&
                    event.getItem().getItemMeta().getDisplayName()
                            .equals("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+ Max")) {
                Player player = (Player) event.getPlayer();
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 1));
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0));
            }
            if (foodInstaKill.contains(event.getItem().getType())) {
                event.getPlayer().addPotionEffect(
                        new PotionEffect(PotionEffectType.HARM, 1, 89));
            }
        }

        if (days >= 12) {
            if (event.getItem().getType() == Material.PUMPKIN_PIE && days < 14) {
                Player player = (Player) event.getPlayer();
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.SATURATION, 200, 1));
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Integer days = plugin.getDays();
        Player player = (Player) event.getWhoClicked();

        if (days >= 6) {
            if (event.getClickedInventory() == player.getInventory()) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() == Material.STRUCTURE_VOID) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBucket(PlayerBucketFillEvent event) {
        Integer days = plugin.getDays();
        if (days >= 12) {
            if (event.getBlockClicked().getType() == Material.WATER) {
                // El jugador recogió agua en un cubo
                event.setCancelled(true);
            } else if (event.getBlockClicked().getType() == Material.LAVA) {
                // El jugador recogió lava en un cubo
                event.setCancelled(true);
            }
        }

    }

    public final List<Material> bedTypes = Arrays.asList(
            Material.RED_BED,
            Material.BLACK_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.CYAN_BED,
            Material.GRAY_BED,
            Material.GREEN_BED,
            Material.LIGHT_BLUE_BED,
            Material.LIGHT_GRAY_BED,
            Material.LIME_BED,
            Material.MAGENTA_BED,
            Material.ORANGE_BED,
            Material.PINK_BED,
            Material.PURPLE_BED,
            Material.WHITE_BED,
            Material.YELLOW_BED);

    private final List<Material> foodInstaKill = Arrays.asList(
            Material.COOKED_BEEF,
            Material.COOKED_CHICKEN,
            Material.COOKED_PORKCHOP,
            Material.COOKED_MUTTON,
            Material.COOKED_BEEF,
            Material.BEEF,
            Material.MUTTON,
            Material.PORKCHOP,
            Material.CHICKEN);
}
