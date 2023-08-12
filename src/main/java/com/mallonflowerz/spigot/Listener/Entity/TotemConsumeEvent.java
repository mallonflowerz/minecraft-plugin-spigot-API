package com.mallonflowerz.spigot.Listener.Entity;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Message;

public class TotemConsumeEvent implements Listener {

    private Plugin plugin;
    private boolean isDeath = false;
    private Message message = new Message();

    public TotemConsumeEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        Integer days = plugin.getDays();
        Integer prob = plugin.getProb();
        if (days >= 8) {
            if (event.getEntityType() == EntityType.PLAYER) {
                Player player = (Player) event.getEntity();
                int random = new Random().nextInt(100);
                if (days >= 12) {
                    if (hasEnoughTotems(player)) {
                        removeTotems(player);
                    } else {
                        message.sendMessage(
                                String.format(
                                        "&4El jugador %s no tenia suficientes totems en su inventario. Ha muerto :(",
                                        player.getName().toUpperCase()));
                        event.setCancelled(true);
                        isDeath = true;
                    }
                }
                if (random < prob) {
                    if (!isDeath) {
                        message.sendMessage(
                                String.format(
                                        "&4El jugador %s ha activado totem, Su probabilidad ha sido %s/100. \nHasta la vista Beiby!",
                                        player.getName().toUpperCase(), random));
                        isDeath = false;
                    }
                    player.setHealth(0);
                } else {
                    if (!isDeath) {
                        message.sendMessage(
                                String.format(
                                        "&4El jugador %s ha activado totem, Su probabilidad ha sido %s/100.",
                                        player.getName().toUpperCase(), random));
                        isDeath = false;
                    }
                }
            }
        }

    }

    private boolean hasEnoughTotems(Player player) {
        int totemCount = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                totemCount += item.getAmount();
                if (totemCount >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    private void removeTotems(Player player) {
        int totemsToRemove = 2;

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);

            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                int amount = item.getAmount();

                if (amount >= totemsToRemove) {
                    item.setAmount(amount - totemsToRemove);
                    totemsToRemove = 0;
                    break;
                } else {
                    totemsToRemove -= amount;
                    item.setAmount(0);
                }
            }
        }

        if (totemsToRemove > 0) {
            player.setHealth(0);
        }
    }

}
