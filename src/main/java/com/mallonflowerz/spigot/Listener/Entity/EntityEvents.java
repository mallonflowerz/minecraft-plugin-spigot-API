package com.mallonflowerz.spigot.Listener.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
import com.mallonflowerz.spigot.items.Bow;
import com.mallonflowerz.spigot.items.DefinitiveArmor;
import com.mallonflowerz.spigot.statics.Mundos;
import com.mallonflowerz.spigot.statics.Potions;

public class EntityEvents implements Listener {

    private Plugin plugin;
    private final Random random = new Random();
    private Mundos mundos = new Mundos();
    private Map<Chicken, Silverfish> chickenToSilverfish = new HashMap<>();
    private Set<EntityType> entitiesDropClear = new HashSet<>();

    public EntityEvents(Plugin plugin) {
        this.plugin = plugin;
        addEntities();
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
                        creeper.setCustomName("CreeperForSlime");
                        creeper.setCustomNameVisible(false);
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
            } else if (event.getDamager().getType() == EntityType.FIREBALL &&
                    event.getEntityType() == EntityType.PLAYER && mundos.isNether(event.getDamager())) {
                Ghast ghast = (Ghast) ((Projectile) event.getDamager()).getShooter();
                Player player = (Player) event.getEntity();
                if (ghast != null) {
                    applyRandomEffect(player);
                }
            } else if (event.getDamager().getType() == EntityType.WARDEN &&
                    mundos.isOverworld(event.getDamager())) {
                Player player = (Player) event.getEntity();
                if (player != null) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.WITHER, 200, 6));
                }
            } else if (event.getDamager().getType() == EntityType.ARROW &&
                    event.getEntityType() == EntityType.WARDEN) {
                if (random.nextInt(100) + 1 >= 50) {
                    event.setCancelled(true);
                }
            }
        }

        if (days >= 14) {
            if (event.getDamager().getType() == EntityType.ARROW) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.hasCustomEffect(PotionEffectType.GLOWING)) {
                    event.setDamage(event.getDamage() * 2);
                }
                if (event.getEntityType() == EntityType.MAGMA_CUBE && mundos.isNether(event.getEntity())) {
                    Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                    if (player != null) {
                        event.setCancelled(true);
                        player.getWorld().strikeLightning(player.getLocation());
                        player.damage(event.getDamage());
                    }
                }
            } else if (event.getDamager().getType() == EntityType.PHANTOM && mundos.isOverworld(event.getDamager())) {
                Endermite endermite = event.getEntity().getWorld().spawn(event.getEntity().getLocation(),
                        Endermite.class);
                endermite.addPotionEffect(
                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 1, true, false));
            } else if (event.getDamager().getType() == EntityType.CAVE_SPIDER &&
                    event.getEntityType() == EntityType.PLAYER && event.getDamager().getCustomName() != null &&
                    ChatColor.stripColor(event.getDamager().getCustomName()).equals("Cave Spider OP")) {
                Location location = event.getEntity().getLocation();
                event.setDamage(3);
                location.getBlock().setType(Material.COBWEB);
                location.setY(location.getY() + 1);
                location.getBlock().setType(Material.COBWEB);
            }
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
            if (event.getEntityType() == EntityType.SKELETON && mundos.isOverworld(event.getEntity()) &&
                    event.getEntity().getCustomName() != null &&
                    event.getEntity().getCustomName().equals("Skeleton Level 4+")) {
                if (random.nextInt(100) + 1 <= 10) {
                    event.getDrops().add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                }
                event.getDrops().add(new ItemStack(Material.DIAMOND, random.nextInt(10)));
            } else if (event.getEntityType() == EntityType.SKELETON && mundos.isOverworld(event.getEntity())) {
                event.getDrops().add(new ItemStack(Material.DIAMOND, random.nextInt(10)));
            }
        }

        if (days >= 14 && days < 16) {
            if (event.getEntityType() == EntityType.CAVE_SPIDER &&
                    event.getEntity().getCustomName() != null &&
                    ChatColor.stripColor(event.getEntity().getCustomName()).equals("Cave Spider OP")) {
                if (random.nextInt(100) + 1 <= 5) {
                    event.getDrops().add(Bow.craftBow());
                }
            } else if (event.getEntityType() == EntityType.GHAST &&
                    event.getEntity().getCustomName() != null &&
                    ChatColor.stripColor(event.getEntity().getCustomName()).equals("Ghast Impredecible")) {
                if (random.nextInt(100) + 1 <= 30) {
                    event.getDrops().add(DefinitiveArmor.craftDefinitiveChest());
                }
            } else if (event.getEntityType() == EntityType.SLIME &&
                    event.getEntity().getCustomName() != null &&
                    ChatColor.stripColor(event.getEntity().getCustomName()).equals("Giga Slime Corrosivo")) {
                if (random.nextInt(100) + 1 <= 10) {
                    event.getDrops().add(DefinitiveArmor.craftDefinitiveHelmet());
                }
            } else if (event.getEntityType() == EntityType.WARDEN &&
                    mundos.isOverworld(event.getEntity())) {
                if (random.nextInt(100) + 1 <= 30) {
                    event.getDrops().add(DefinitiveArmor.craftDefinitiveLeggings());
                }
            } else if (event.getEntityType() == EntityType.WITHER &&
                    mundos.isOverworld(event.getEntity())
                    && event.getEntity().getCustomName() != null &&
                    ChatColor.stripColor(event.getEntity().getCustomName()).equals("Wither Doble W")) {
                if (random.nextInt(100) + 1 <= 30) {
                    event.getDrops().add(DefinitiveArmor.craftDefinitiveLeggings());
                }
            }
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
    public void onEntityDamage(EntityDamageEvent event) {
        Integer days = plugin.getDays();
        if (days >= 14) {
            if (event.getCause() == EntityDamageEvent.DamageCause.LAVA && event.getEntityType() == EntityType.PLAYER) {
                event.setDamage(6.0);
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
            Player player = (Player) event.getEntity();

            // Verificar si el jugador está usando una ballesta
            if (player.getEquipment().getItemInMainHand().getType().toString().contains("CROSSBOW")) {
                Arrow arrow = (Arrow) event.getProjectile();

                // Aplicar el multiplicador de daño (x3)
                double newDamage = arrow.getDamage() * 3;
                arrow.setDamage(newDamage);
            }
        } else if (event.getEntityType() == EntityType.WARDEN) {
            Warden warden = (Warden) event.getEntity();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().distance(warden.getLocation()) < 100.0) {
                    TNTPrimed tnt = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
                    tnt.setFuseTicks(0);
                    tnt.setVisibleByDefault(false);
                }
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

    private void applyRandomEffect(LivingEntity entity) {
        PotionEffectType[] effects = {
                PotionEffectType.POISON,
                PotionEffectType.WITHER,
                PotionEffectType.SLOW,
                PotionEffectType.LEVITATION,
                PotionEffectType.BAD_OMEN,
        };

        int maxEffects = 5; // Máximo número de efectos que pueden aplicarse
        int numEffects = random.nextInt(maxEffects) + 1; // Número aleatorio de efectos a aplicar

        for (int i = 0; i < numEffects; i++) {
            PotionEffectType effectType = effects[random.nextInt(effects.length)];
            double damage = random.nextInt(40) + 1;
            int effectDuration = random.nextInt(5) + 1 * 1200;
            int effectLevel = random.nextInt(5) + 1; // Nivel entre 1 y 5

            PotionEffect potionEffect = new PotionEffect(effectType, effectDuration, effectLevel);
            entity.addPotionEffect(potionEffect);
            entity.damage(damage);
        }
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
