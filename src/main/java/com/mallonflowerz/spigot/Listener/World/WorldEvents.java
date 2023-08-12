package com.mallonflowerz.spigot.Listener.World;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.items.RecipeDia10;
import com.mallonflowerz.spigot.items.RecipeDia6;

public class WorldEvents implements Listener {

    private Plugin plugin;
    private boolean isRaining = false;
    private Set<EntityType> mobsPasives = new HashSet<>();
    private Set<EntityType> entitiesDropClear = new HashSet<>();
    private RecipeDia6 recipes;
    private RecipeDia10 recipesDay10;

    public WorldEvents(final Plugin plugin) {
        this.plugin = plugin;
        recipes = new RecipeDia6(plugin);
        recipesDay10 = new RecipeDia10(plugin);
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                addMobsPasives();
                addEntities();
                for (Player player : plugin.getServer().getOnlinePlayers()) {

                    if (plugin.getDays() >= 6) {
                        Inventory inventory = player.getInventory();
                        if (inventory == null) {
                            return;
                        }
                        if (player.getInventory().getHelmet() != null &&
                                player.getInventory().getHelmet().isSimilar(recipes.getCustomHelmetItem())) {
                            recipes.removeNegativeEffect(player);
                        } else {
                            // recipes.applyNegativeEffect(player);
                        }
                        if (player.getInventory().contains(recipes.getCustomBarrierItem())) {
                            desblockInventory(player);
                        } else {
                            blockedInventory(player);
                        }
                    } else if (plugin.getDays() < 6) {
                        desblockInventory(player);
                    }

                    if (plugin.getDays() >= 10) {
                        onRainingAcid(player);
                    }

                    if (plugin.getDays() >= 12) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(16.0);
                    }

                }
            }
        }, 0L, 0L);

        if (plugin.getDays() >= 6) {
            plugin.getServer().addRecipe(recipes.registerCustomBarrierCrafting());
            plugin.getServer().addRecipe(recipes.registerCustomRecipe());
        }

        if (plugin.getDays() >= 8) {
            plugin.getServer().addRecipe(recipesDay10.registerGoldenApplePlusMaxRecipe());
            plugin.getServer().addRecipe(recipesDay10.registerGoldenApplePlusRecipe());
            plugin.getServer().addRecipe(recipesDay10.registerShieldOpRecipe());
        }

    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            isRaining = true;
        } else {
            isRaining = false;
        }
    }

    private void blockedInventory(Player player) {
        ItemStack itemStack = new ItemStack(Material.STRUCTURE_VOID);
        player.getInventory().setItem(4, itemStack);
        player.getInventory().setItem(31, itemStack);
        player.getInventory().setItem(22, itemStack);
        player.getInventory().setItem(13, itemStack);
        player.getInventory().setItem(40, itemStack);
    }

    private void desblockInventory(Player player) {
        if (player.getInventory().contains(Material.STRUCTURE_VOID)) {
            player.getInventory().setItem(4, null);
            player.getInventory().setItem(31, null);
            player.getInventory().setItem(22, null);
            player.getInventory().setItem(13, null);
            player.getInventory().setItem(40, null);
        }
    }

    private void onRainingAcid(Player player) {
        if (isRaining && !hasSolidBlockAbove(player)) {
            player.damage(1);
        }
    }

    private boolean hasSolidBlockAbove(Player player) {
        int maxY = player.getLocation().getBlockY() + 60; // Altura máxima a la que se va a evaluar
        for (int y = player.getLocation().getBlockY() + 1; y <= maxY; y++) {
            Material blockType = player.getWorld()
                    .getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ()).getType();
            if (blockType.isSolid()) {
                return true;
            }
        }
        return false;
    }

    public void addMobsPasives() {
        mobsPasives.add(EntityType.ALLAY);
        mobsPasives.add(EntityType.AXOLOTL);
        mobsPasives.add(EntityType.BAT);
        mobsPasives.add(EntityType.CAT);
        // mobsPasives.add(EntityType.CHICKEN);
        mobsPasives.add(EntityType.COD);
        mobsPasives.add(EntityType.COW);
        mobsPasives.add(EntityType.DONKEY);
        mobsPasives.add(EntityType.DOLPHIN);
        mobsPasives.add(EntityType.FOX);
        mobsPasives.add(EntityType.GOAT);
        mobsPasives.add(EntityType.HOGLIN);
        mobsPasives.add(EntityType.HORSE);
        mobsPasives.add(EntityType.MULE);
        mobsPasives.add(EntityType.OCELOT);
        mobsPasives.add(EntityType.PIG);
        mobsPasives.add(EntityType.POLAR_BEAR);
        mobsPasives.add(EntityType.RABBIT);
        mobsPasives.add(EntityType.SHEEP);
        mobsPasives.add(EntityType.SKELETON_HORSE);
        mobsPasives.add(EntityType.SQUID);
        mobsPasives.add(EntityType.STRIDER);
        mobsPasives.add(EntityType.VILLAGER);
    }

    public void addEntities() {
        entitiesDropClear.add(EntityType.IRON_GOLEM);
        entitiesDropClear.add(EntityType.ZOMBIFIED_PIGLIN);
        entitiesDropClear.add(EntityType.GHAST);
        entitiesDropClear.add(EntityType.GUARDIAN);
        entitiesDropClear.add(EntityType.MAGMA_CUBE);
        entitiesDropClear.add(EntityType.ENDERMAN);
        entitiesDropClear.add(EntityType.WITCH);
        entitiesDropClear.add(EntityType.WITHER_SKELETON);
        entitiesDropClear.add(EntityType.EVOKER);
        entitiesDropClear.add(EntityType.PHANTOM);
        entitiesDropClear.add(EntityType.SLIME);
        entitiesDropClear.add(EntityType.DROWNED);
        entitiesDropClear.add(EntityType.BLAZE);
        entitiesDropClear.add(EntityType.ZOMBIE);
    }
}
