package com.mallonflowerz.spigot.days;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.mallonflowerz.spigot.creatures.dia_6.Blazes;
import com.mallonflowerz.spigot.creatures.dia_6.Creepers;
import com.mallonflowerz.spigot.creatures.dia_6.Skeletons;
import com.mallonflowerz.spigot.creatures.dia_6.WitherSkeletons;
import com.mallonflowerz.spigot.statics.Mundos;
import com.mallonflowerz.spigot.statics.Potions;

public class Dia_6 implements Listener {

    private final Skeletons skeletons = new Skeletons();
    private final Blazes blazes = new Blazes();
    private final Creepers creepers = new Creepers();
    private final WitherSkeletons witherSkeletons = new WitherSkeletons();
    private final Random random = new Random();
    private Map<Chicken, Silverfish> chickenToSilverfish = new HashMap<>();

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.SKELETON &&
                event.getLocation().getWorld().getName().equals(Mundos.WORLD_OVERWORLD)) {
            LivingEntity skeleton = event.getEntity();
            int randomNumber = new Random().nextInt(4);

            Skeleton skeletonMethod = null;
            switch (randomNumber) {
                case 0:
                    skeletonMethod = skeletons.spawnSkeletonOne(event);
                    break;
                case 1:
                    skeletonMethod = skeletons.spawnSkeletonTwo(event);
                    break;
                case 2:
                    skeletonMethod = skeletons.spawnSkeletonThree(event);
                    break;
                case 3:
                    skeletonMethod = skeletons.spawnSkeletonFour(event);
                    break;
            }

            skeleton = skeletonMethod;
        } else if (event.getEntityType() == EntityType.BLAZE) {
            LivingEntity blaze = event.getEntity();
            blaze = blazes.onSpawnBlaze(event);
        } else if (event.getEntityType() == EntityType.CREEPER) {
            LivingEntity creeper = event.getEntity();
            creeper = creepers.onSpawnCreeper(event);
        } else if (event.getEntityType() == EntityType.ZOMBIE) {
            event.setCancelled(true);
            event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.VINDICATOR);
        } else if (event.getEntityType() == EntityType.WITHER_SKELETON &&
                event.getEntity().getWorld().getName().equals(Mundos.WORLD_NETHER)) {
            double randomValue = new Random().nextDouble() * 100;
            if (randomValue <= 40.0) {
                LivingEntity witherSkeleton = event.getEntity();
                witherSkeleton = witherSkeletons.onSpawnWitherSkeleton(event);
            }

        } else if (event.getEntityType() == EntityType.CHICKEN &&
                event.getEntity().getWorld().getName().equals(Mundos.WORLD_OVERWORLD)) {
            Chicken chicken = (Chicken) event.getEntity();
            Entity silverfish = chicken.getWorld().spawnEntity(chicken.getLocation(), EntityType.SILVERFISH);
            silverfish.setCustomName("Silver");
            silverfish.setCustomNameVisible(false);
            silverfish.setSilent(true);
            silverfish.setVisibleByDefault(false);
            // chicken.setCustomName("PolloInSilver");
            // chicken.setCustomNameVisible(false);
            chicken.addPassenger(silverfish);
            chicken.addPotionEffect(new PotionEffect(Potions.SP, Integer.MAX_VALUE, 2, true, false));

            chickenToSilverfish.put(chicken, (Silverfish) silverfish);
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER &&
                event.getDamager().getType() == EntityType.ARROW) {
            Skeleton skeleton = (Skeleton) ((Projectile) event.getDamager()).getShooter();
            if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 1")) {
                event.setDamage(8);
            } else if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 2")) {
                event.setDamage(12);
            } else if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 4")) {
                event.setDamage(20);
            }
        } else if (event.getEntityType() == EntityType.PLAYER &&
                (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null &&
                        event.getDamager().getCustomName().equals("Skeleton Level 3"))) {
            event.setDamage(16);
        } else if (event.getEntityType() == EntityType.PLAYER &&
                event.getDamager().getType() == EntityType.WITHER_SKELETON &&
                (event.getDamager().getCustomName() != null && event.getDamager().getCustomName() != null &&
                        event.getDamager().getCustomName().equals("Wither Skeleton The Golden"))) {
            event.setDamage(25);
        } else if (event.getEntityType() == EntityType.WITHER_SKELETON &&
                (event.getEntity().getCustomName() != null && event.getEntity().getCustomName() != null &&
                        event.getEntity().getCustomName().equals("Wither Skeleton The Golden"))
                &&
                event.getDamager().getType() == EntityType.ARROW) {
            event.setCancelled(true);
        } else if (event.getEntityType() == EntityType.PLAYER &&
                (event.getDamager().getType() == EntityType.SILVERFISH && event.getDamager().getCustomName() != null &&
                        event.getDamager().getCustomName().equals("Silver"))) {
            Player player = (Player) event.getEntity();

            ItemStack[] inventoryContents = player.getInventory().getContents();
            ItemStack randomItem = getRandomItem(inventoryContents);
            if (randomItem != null) {
                // Verificar y eliminar el item del inventario del jugador
                if (player.getInventory().contains(randomItem)) {
                    player.getInventory().remove(randomItem);
                } else {
                    // Verificar y eliminar el item de las ranuras de armadura del jugador
                    ItemStack[] armorContents = player.getInventory().getArmorContents();
                    for (int i = 0; i < armorContents.length; i++) {
                        if (armorContents[i] != null && armorContents[i].equals(randomItem)) {
                            armorContents[i] = null;
                            player.getInventory().setArmorContents(armorContents);
                            break;
                        }
                    }
                    // Verificar y eliminar el item de la mano izquierda del jugador
                    if (player.getInventory().getItemInOffHand().equals(randomItem)) {
                        player.getInventory().setItemInOffHand(null);
                    }
                }
                player.getWorld().dropItem(player.getLocation().add(1, 0, 1), randomItem);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.removePotionEffect(Potions.SP);
            creeper.removePotionEffect(Potions.DR);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();

        if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.IRON_ORE ||
                block.getType() == Material.GOLD_ORE || block.getType() == Material.REDSTONE_ORE ||
                block.getType() == Material.LAPIS_ORE || block.getType() == Material.EMERALD_ORE ||
                block.getType() == Material.ANCIENT_DEBRIS) {
            player.damage(5);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == player.getInventory()) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.STRUCTURE_VOID) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Block block = (Block) event.getBlock();
        if (block.getType() == Material.STRUCTURE_VOID) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        if (dropped.getType() == Material.STRUCTURE_VOID) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.CHICKEN &&
                chickenToSilverfish.containsKey(event.getEntity())) {
            Silverfish silverfish = chickenToSilverfish.get(event.getEntity());
            if (silverfish != null) {
                silverfish.setHealth(0);
            }

            chickenToSilverfish.remove(event.getEntity());
        }
    }

    public ItemStack getRandomItem(ItemStack[] inventoryContents) {
        ItemStack randomItem = null;
        int itemCount = 0;
        for (ItemStack item : inventoryContents) {
            if (item != null) {
                itemCount++;
                if (random.nextInt(itemCount) == 0) {
                    randomItem = item;
                }
            }
        }
        return randomItem;
    }

    public void blockedInventory(Player player) {
        ItemStack itemStack = new ItemStack(Material.STRUCTURE_VOID);
        player.getInventory().setItem(4, itemStack);
        player.getInventory().setItem(31, itemStack);
        player.getInventory().setItem(22, itemStack);
        player.getInventory().setItem(13, itemStack);
        player.getInventory().setItem(40, itemStack);
    }

    public void desblockInventory(Player player) {
        if (player.getInventory().contains(Material.STRUCTURE_VOID)) {
            player.getInventory().setItem(4, null);
            player.getInventory().setItem(31, null);
            player.getInventory().setItem(22, null);
            player.getInventory().setItem(13, null);
            player.getInventory().setItem(40, null);
        }
    }

}
