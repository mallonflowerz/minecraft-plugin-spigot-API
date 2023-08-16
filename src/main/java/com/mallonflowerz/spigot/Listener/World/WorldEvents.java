package com.mallonflowerz.spigot.Listener.World;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.items.DefinitiveArmor;
import com.mallonflowerz.spigot.items.RecipeDia10;
import com.mallonflowerz.spigot.items.RecipeDia6;

public class WorldEvents implements Listener {

    private Plugin plugin;
    private boolean isRaining = false;
    private RecipeDia6 recipes;
    private RecipeDia10 recipesDay10;

    public WorldEvents(final Plugin plugin) {
        this.plugin = plugin;
        recipes = new RecipeDia6(plugin);
        recipesDay10 = new RecipeDia10(plugin);
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {

                    if (plugin.getDays() >= 6) {
                        Inventory inventory = player.getInventory();
                        if (inventory == null) {
                            return;
                        }
                        if (RecipeDia6.isSpaceHelmet(player.getInventory().getHelmet()) ||
                                DefinitiveArmor.isDefinitivePiece(player.getInventory().getHelmet())) {
                            recipes.removeNegativeEffect(player);
                        } else {
                            recipes.applyNegativeEffect(player);
                        }
                        if (RecipeDia6.isClean(player.getInventory())) {
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
                        ItemStack hel = player.getInventory().getHelmet();
                        ItemStack ches = player.getInventory().getChestplate();
                        ItemStack legg = player.getInventory().getLeggings();
                        ItemStack bot = player.getInventory().getBoots();

                        if (DefinitiveArmor.isDefinitivePiece(hel) &&
                                DefinitiveArmor.isDefinitivePiece(ches) &&
                                DefinitiveArmor.isDefinitivePiece(legg) &&
                                DefinitiveArmor.isDefinitivePiece(bot)) {
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                                    .setBaseValue(
                                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 4.0);
                            player.addPotionEffect(
                                    new PotionEffect(PotionEffectType.SPEED, 20, 0));
                            player.addPotionEffect(
                                    new PotionEffect(PotionEffectType.REGENERATION, 20, 0));
                            player.addPotionEffect(
                                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
                        }
                    }

                    if (plugin.getDays() >= 14) {
                        if (player.isInWater()) {
                            player.addPotionEffect(
                                    new PotionEffect(PotionEffectType.SLOW, 10, 29, true, false));
                        }
                    }
                }
            }
        }, 0L, 0L);

        if (plugin.getDays() >= 6) {
            plugin.getServer().addRecipe(recipes.registerCustomBarrierCrafting());
            plugin.getServer().addRecipe(recipes.registerCustomRecipe());
        }

        if (plugin.getDays() >= 8) {
            if (plugin.getDays() <= 14) {
                plugin.getServer().addRecipe(recipesDay10.registerGoldenApplePlusMaxRecipe());
                plugin.getServer().addRecipe(recipesDay10.registerGoldenApplePlusRecipe());
            }
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
        Inventory inventory = player.getInventory();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        // Verificar si el ítem de la mano izquierda es el que queremos eliminar
        if (offHandItem != null && offHandItem.getType() == Material.STRUCTURE_VOID) {
            player.getInventory().setItemInOffHand(null);
        }

        // Recorrer el inventario en busca de ocurrencias del ítem a eliminar
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() == Material.STRUCTURE_VOID) {
                inventory.setItem(i, null);
            }
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
}
