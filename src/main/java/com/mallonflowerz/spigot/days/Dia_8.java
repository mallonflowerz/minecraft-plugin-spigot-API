package com.mallonflowerz.spigot.days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import com.mallonflowerz.spigot.statics.Message;
import com.mallonflowerz.spigot.statics.Mundos;
import com.mallonflowerz.spigot.statics.Potions;

public class Dia_8 implements Listener {

    private Set<EntityType> entitiesDropClear = new HashSet<>();
    private Set<EntityType> mobsPasives = new HashSet<>();
    private Map<EntityType, Zombie> entityToZombie = new HashMap<>();
    private Message message = new Message();
    private Random random = new Random();
    private Dia_6 dia_6 = new Dia_6();

    @EventHandler
    public void onStepFire(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        Location from = event.getFrom();
        Location to = event.getTo();

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

    @EventHandler
    public void onSpawnEntity(CreatureSpawnEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            LivingEntity entity = event.getEntity();
            entity.addPotionEffect(
                    new PotionEffect(Potions.FR, Integer.MAX_VALUE, 0, true, false));
            if (mobsPasives.contains(entity.getType())) {
                Zombie zombie = (Zombie) entity.getLocation().getWorld().spawnEntity(entity.getLocation(),
                        EntityType.ZOMBIE);
                zombie.setCustomName("ZombieAll");
                zombie.setCustomNameVisible(false);
                zombie.setSilent(true);

                zombie.setVisibleByDefault(false);

                zombie.addPotionEffect(
                        new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
                entity.addPassenger(zombie);
                entity.addPotionEffect(new PotionEffect(Potions.SP, Integer.MAX_VALUE, 1, true, false));

                entityToZombie.put(entity.getType(), zombie);
            }
        }
        if (event.getEntityType() == EntityType.ENDERMAN)
            event.getEntity().setCustomName("Vampiro Enderman");
        if (event.getEntityType() == EntityType.SLIME)
            event.getEntity().setCustomName("Slime Corrosivo");
        if (event.getEntityType() == EntityType.SPIDER ||
                event.getEntityType() == EntityType.CAVE_SPIDER)
            applyRandomEffect(event.getEntity());
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE &&
                event.getTarget() != null && event.getTarget().getType() != EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityTransform(EntityTransformEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE
                && event.getTransformedEntity().getType() == EntityType.DROWNED) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
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
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            int random = new Random().nextInt(100);
            if (random < 2) {
                message.sendMessage(
                        String.format(
                                "&4El jugador %s ha activado totem, Su probabilidad ha sido %s/100. \nHasta la vista Beiby!",
                                player.getName().toUpperCase(), random));
                player.setHealth(0);
            } else {
                message.sendMessage(
                        String.format(
                                "&4El jugador %s ha activado totem, Su probabilidad ha sido %s/100.",
                                player.getName().toUpperCase(), random));
            }
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Block bed = event.getBed();
        if (player.getWorld().getTime() < 13000) {
            event.getPlayer().sendMessage(String
                    .format(ChatColor.translateAlternateColorCodes('&', "&4No se puede dormir ahora!")));
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

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.MILK_BUCKET)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER &&
                (event.getDamager().getType() == EntityType.ZOMBIE && event.getDamager().getCustomName() != null &&
                        event.getDamager().getCustomName().equals("ZombieAll"))) {
            Player player = (Player) event.getEntity();

            ItemStack[] inventoryContents = player.getInventory().getContents();
            ItemStack randomItem = dia_6.getRandomItem(inventoryContents);
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
                event.getEntity().getLocation().getWorld().getName().equals(Mundos.WORLD_OVERWORLD)) {
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
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getEntity().getShooter() instanceof Witch &&
                event.getEntity().getLocation().getWorld().getName().equals(Mundos.WORLD_OVERWORLD)) {
            // Agregar efecto de daño instantáneo al lanzar la poción.
            for (Entity entity : event.getAffectedEntities()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 0));
                }
            }
        }
    }

    private void applyRandomEffect(LivingEntity entity) {
        PotionEffectType[] effects = {
                PotionEffectType.SPEED,
                PotionEffectType.REGENERATION,
                PotionEffectType.DAMAGE_RESISTANCE,
                PotionEffectType.GLOWING,
                PotionEffectType.INVISIBILITY,
                PotionEffectType.JUMP,
                PotionEffectType.HEALTH_BOOST,
                PotionEffectType.INCREASE_DAMAGE
        };

        int maxEffects = 7; // Máximo número de efectos que pueden aplicarse
        int numEffects = random.nextInt(maxEffects) + 1; // Número aleatorio de efectos a aplicar

        for (int i = 0; i < numEffects; i++) {
            PotionEffectType effectType = effects[random.nextInt(effects.length)];
            int effectDuration = Integer.MAX_VALUE;
            int effectLevel = random.nextInt(4) + 1; // Nivel entre 1 y 4

            PotionEffect potionEffect = new PotionEffect(effectType, effectDuration, effectLevel);
            entity.addPotionEffect(potionEffect);
        }
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
