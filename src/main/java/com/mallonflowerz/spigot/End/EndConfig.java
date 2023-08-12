package com.mallonflowerz.spigot.End;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.creatures.dia_10.Entities;
import com.mallonflowerz.spigot.items.CustomLootTable;
import com.mallonflowerz.spigot.statics.Mundos;

public class EndConfig implements Listener {

    private Plugin plugin;
    private final Entities entities = new Entities();
    private final Mundos mundos = new Mundos();
    private final CustomLootTable loot;
    private final Random random = new Random();

    public EndConfig(Plugin plugin) {
        this.plugin = plugin;
        this.loot = new CustomLootTable(plugin);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getEntityType() == EntityType.IRON_GOLEM &&
                    mundos.isEnd(event.getEntity())) {
                IronGolem ironGolem = (IronGolem) event.getEntity();
                ironGolem.addPotionEffect(
                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 1, true, false));
                ironGolem.addPotionEffect(
                        new PotionEffect(PotionEffectType.SPEED, -1, 3));
                ironGolem.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 3, true, false));
                ironGolem.addPotionEffect(
                        new PotionEffect(PotionEffectType.REGENERATION, -1, 1, true, false));
            } else if (event.getEntityType() == EntityType.ENDERMAN &&
                    mundos.isEnd(event.getEntity())) {
                Enderman enderman = (Enderman) event.getEntity();
                enderman.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0, true, false));
                enderman.addPotionEffect(
                        new PotionEffect(PotionEffectType.REGENERATION, -1, 2, true, false));
                if (random.nextInt(100) < 10) {
                    entities.onGhast(event.getLocation());
                }
                if (random.nextInt(100) < 20) {
                    entities.onCreeper(event.getLocation());
                }
            } else if (event.getEntityType() == EntityType.SHULKER &&
                    mundos.isEnd(event.getEntity())) {
                Shulker shulker = (Shulker) event.getEntity();
                shulker.setCustomName("Shulker TNT");
                shulker.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0, true, false));
            }
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getEntityType() == EntityType.SHULKER &&
                    mundos.isEnd(event.getEntity())) {
                Shulker shulker = (Shulker) event.getEntity();
                TNTPrimed tnt = shulker.getLocation().getWorld().spawn(shulker.getLocation(), TNTPrimed.class);
                tnt.setFireTicks(20);
                if (random.nextInt(100) > 30) {
                    event.getDrops().clear();
                }
            }
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getEntity() instanceof Item) {
                Item item = (Item) event.getEntity();
                if (item.getItemStack().getType() == Material.SHULKER_SHELL) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        event.setCancelled(true);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.SHULKER_BULLET) {
                TNTPrimed tnt = event.getEntity().getLocation().getWorld().spawn(event.getEntity().getLocation(),
                        TNTPrimed.class);
                tnt.setFireTicks(0);
                tnt.setVisibleByDefault(false);
            } else if (event.getEntityType() == EntityType.PLAYER && mundos.isEnd(event.getEntity()) &&
                    event.getDamager().getType() == EntityType.FIREBALL) {
                ProjectileSource shooter = (ProjectileSource) ((Projectile) event.getDamager()).getShooter();
                if (shooter instanceof Ghast) {
                    Fireball fireball = (Fireball) event.getDamager();
                    fireball.setYield(30.0F);
                }
            }
        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            if (event.getEntityType() == EntityType.CREEPER &&
                    event.getEntity().getCustomName() != null && mundos.isEnd(event.getEntity()) &&
                    event.getEntity().getCustomName().equals("Creeper Megalodon")) {
                Creeper creeper = (Creeper) event.getEntity();
                creeper.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                creeper.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                event.blockList().clear();
            }
        }

    }

    @EventHandler
    public void onLootGenerate(LootGenerateEvent event) {
        Integer days = plugin.getDays();
        if (days >= 10) {
            InventoryHolder inventoryHolder = event.getInventoryHolder();
            if (inventoryHolder.getInventory().getLocation().getWorld().getName().equals(Mundos.WORLD_END)) {
                LootContext context = event.getLootContext();
                Collection<ItemStack> customLoot = loot.populateLoot(random, context);

                Inventory inventory = inventoryHolder.getInventory();
                int[] emptySlots = getEmptySlots(inventory.getSize(), inventory);

                for (int i = 0; i < customLoot.size(); i++) {
                    ItemStack item = (ItemStack) customLoot.toArray()[i];
                    int slotIndex = emptySlots[i % emptySlots.length];
                    inventory.setItem(slotIndex, item);
                }

                event.setCancelled(true);
            }
        }

    }

    private int[] getEmptySlots(int size, Inventory inventory) {
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (inventory.getItem(i) == null) {
                emptySlots.add(i);
            }
        }
        // Mezclar los índices de los slots vacíos
        Collections.shuffle(emptySlots);

        int[] emptySlotsArray = new int[emptySlots.size()];
        for (int i = 0; i < emptySlotsArray.length; i++) {
            emptySlotsArray[i] = emptySlots.get(i);
        }

        return emptySlotsArray;
    }

}
