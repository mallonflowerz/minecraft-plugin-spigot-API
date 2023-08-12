package com.mallonflowerz.spigot.Listener.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Mundos;
import com.mallonflowerz.spigot.statics.Potions;

public class EntityEvents implements Listener {

    private Plugin plugin;
    private final Random random = new Random();
    private Mundos mundos = new Mundos();
    private Map<Chicken, Silverfish> chickenToSilverfish = new HashMap<>();
    private Map<EntityType, Zombie> entityToZombie = new HashMap<>();
    private Set<EntityType> entitiesDropClear = new HashSet<>();

    public EntityEvents(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityShootBow(EntityDamageByEntityEvent event) {
        Integer days = plugin.getDays();
        if (days >= 6) {
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
                    (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null
                            &&
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
                    (event.getDamager().getType() == EntityType.SILVERFISH && event.getDamager().getCustomName() != null
                            &&
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

        if (days >= 8) {
            if (event.getEntityType() == EntityType.PLAYER &&
                    (event.getDamager().getType() == EntityType.ZOMBIE && event.getDamager().getCustomName() != null &&
                            event.getDamager().getCustomName().equals("ZombieAll"))) {
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
            if (event.getEntityType() == EntityType.ENDERMAN &&
                    mundos.isOverworld(event.getEntity())) {
                if (event.getEntity().getWorld().getTime() > 12000) {
                    Enderman enderman = (Enderman) event.getEntity();
                    enderman.addPotionEffect(
                            new PotionEffect(Potions.RE, 100, 5));
                }
            }
            if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.SLIME) {
                Player player = (Player) event.getEntity();
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.POISON, 400, 1));
            }
            if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.MAGMA_CUBE) {
                event.getEntity().setFireTicks(600);
            }
            if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.ARROW) {
                Skeleton skeleton = (Skeleton) ((Projectile) event.getDamager()).getShooter();
                Player player = (Player) event.getEntity();
                if (skeleton != null && player != null) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.SLOW, 200, 1));
                }
            }
        }

        if (days >= 10) {
            if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.ARROW) {
                Skeleton skeleton = (Skeleton) ((Projectile) event.getDamager()).getShooter();
                if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 1+")) {
                    event.setDamage(16);
                } else if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 2+")) {
                    event.setDamage(20);
                }
            } else if (event.getEntityType() == EntityType.PLAYER &&
                    (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null
                            &&
                            event.getDamager().getCustomName().equals("Skeleton Level 3+"))) {
                event.setDamage(24);
            } else if (event.getEntityType() == EntityType.PLAYER &&
                    (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null
                            &&
                            event.getDamager().getCustomName().equals("Skeleton Level 4+"))) {
                Player player = (Player) event.getEntity();
                event.setDamage(36);
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.SLOW, 100, 1));
            } else if (event.getEntityType() == EntityType.PLAYER &&
                    event.getDamager().getType() == EntityType.SILVERFISH &&
                    mundos.isOverworld(event.getDamager())) {
                Player player = (Player) event.getEntity();
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.HARM, 1, 100));
            }
        }

        if (days >= 12) {
            if (event.getDamager().getType() == EntityType.SLIME &&
                    event.getEntityType() == EntityType.PLAYER) {
                int number = random.nextInt(3);
                Location location = event.getEntity().getLocation();
                location.setX(location.getX() + 3);
                location.setZ(location.getZ() + 3);
                Player player = (Player) event.getEntity();

                switch (number) {
                    case 0:
                        Witch witch = (Witch) event.getEntity().getWorld().spawnEntity(location, EntityType.WITCH);
                        witch.setTarget(player);
                        break;
                    case 1:
                        Creeper creeper = (Creeper) event.getEntity().getWorld().spawnEntity(location,
                                EntityType.CREEPER);
                        creeper.setTarget(player);
                        break;
                    case 2:
                        Skeleton skeleton = (Skeleton) event.getEntity().getWorld().spawnEntity(location,
                                EntityType.SKELETON);
                        skeleton.setTarget(player);
                        break;
                    case 3:
                        Slime slime = (Slime) event.getEntity().getWorld().spawnEntity(location, EntityType.SLIME);
                        slime.setTarget(player);
                        break;
                }
            } else if (event.getDamager().getType() == EntityType.ARROW &&
                    event.getEntityType() == EntityType.SLIME) {
                Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                if (player != null) {
                    if (random.nextInt(100) + 1 > 10) {
                        event.setCancelled(true);
                    }
                    if (random.nextInt(100) + 1 <= 20) {
                        player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
                    }
                    if (random.nextInt(100) + 1 <= 40) {
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }
            }
        }

        if (days >= 14) {

        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.removePotionEffect(Potions.SP);
            creeper.removePotionEffect(Potions.DR);
            creeper.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            creeper.removePotionEffect(PotionEffectType.INVISIBILITY);
            creeper.removePotionEffect(PotionEffectType.REGENERATION);
            creeper.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Integer days = plugin.getDays();
        if (days >= 6) {
            if (event.getEntityType() == EntityType.CHICKEN &&
                    chickenToSilverfish.containsKey(event.getEntity())) {
                Silverfish silverfish = chickenToSilverfish.get(event.getEntity());
                if (silverfish != null) {
                    silverfish.setHealth(0);
                }
                chickenToSilverfish.remove(event.getEntity());
            }
        }

        if (days >= 8) {
            if (entityToZombie.containsKey(event.getEntityType())) {
                Zombie zombie = entityToZombie.get(event.getEntityType());
                if (zombie != null) {
                    zombie.setHealth(0);
                }
                entityToZombie.remove(event.getEntityType());
            }
            if (entitiesDropClear.contains(event.getEntityType())) {
                event.getDrops().clear();
            }
            if (event.getEntityType() == EntityType.WITCH &&
                    mundos.isNether(event.getEntity())) {
                ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);
                event.getDrops().add(goldBlock);
            }
            if (event.getEntityType() == EntityType.PIGLIN_BRUTE &&
                    mundos.isNether(event.getEntity())) {
                ItemStack diamondBlock = new ItemStack(Material.DIAMOND_BLOCK);
                event.getDrops().add(diamondBlock);
            }
            if (event.getEntityType() == EntityType.GHAST &&
                    mundos.isNether(event.getEntity())) {
                Ghast ghast = (Ghast) event.getEntity();
                TNTPrimed tnt = ghast.getWorld().spawn(ghast.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
            }
        }

        if (days >= 12) {
        }
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        Integer days = plugin.getDays();
        if (days >= 8) {
            if (event.getEntityType() == EntityType.ZOMBIE &&
                    event.getTarget() != null && event.getTarget().getType() != EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onEntityTransform(EntityTransformEvent event) {
        Integer days = plugin.getDays();
        if (days >= 8) {
            if (event.getEntityType() == EntityType.ZOMBIE
                    && event.getTransformedEntity().getType() == EntityType.DROWNED) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        Integer days = plugin.getDays();
        if (days >= 8) {
            if (event.getEntity().getShooter() instanceof Witch &&
                    mundos.isNether(event.getEntity())) {
                // Agregar efecto de daño instantáneo al lanzar la poción.
                for (Entity entity : event.getAffectedEntities()) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 0));
                    }
                }
            }
        }

    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player
                && event.getProjectile() instanceof Arrow) {
            Player entity = (Player) event.getEntity();

            // Verificar si el jugador está usando una ballesta
            if (entity.getEquipment().getItemInMainHand().getType().toString().contains("CROSSBOW")) {
                Arrow arrow = (Arrow) event.getProjectile();

                // Aplicar el multiplicador de daño (x3)
                double newDamage = arrow.getDamage() * 3;
                arrow.setDamage(newDamage);
            }
        }
    }

    private ItemStack getRandomItem(ItemStack[] inventoryContents) {
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
}
