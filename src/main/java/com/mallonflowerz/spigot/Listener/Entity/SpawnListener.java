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
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.creatures.dia_10.SkeletonsLevelUp;
import com.mallonflowerz.spigot.creatures.dia_4.Spiders;
import com.mallonflowerz.spigot.creatures.dia_6.Blazes;
import com.mallonflowerz.spigot.creatures.dia_6.Creepers;
import com.mallonflowerz.spigot.creatures.dia_6.Skeletons;
import com.mallonflowerz.spigot.creatures.dia_6.WitherSkeletons;
import com.mallonflowerz.spigot.statics.Mundos;
import com.mallonflowerz.spigot.statics.Potions;

public class SpawnListener implements Listener {

    public SpawnListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Integer days = plugin.getDays();
        // Dia 2
        if (days >= 2) {
            if (event.getEntityType() != EntityType.PLAYER) {
                LivingEntity entity = event.getEntity();
                entity.addPotionEffects(Potions.potions());
            }
        }

        // Dia 4
        if (days >= 4) {
            if (event.getEntityType() == EntityType.SPIDER) {
                spiders.applyRandomEffect(event.getEntity());

            } else if (event.getEntityType() != EntityType.PLAYER && event.getEntityType() != EntityType.SPIDER) {
                LivingEntity entity = event.getEntity();
                entity.addPotionEffects(Potions.potions());
            }
        }

        // Dia 6
        if (days >= 6) {
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
                    witherSkeletons.onSpawnWitherSkeleton(event.getLocation());
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

        // Dia 8
        if (days >= 8) {
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
                if (event.getEntityType() == EntityType.ENDERMAN &&
                        mundos.isOverworld(event.getEntity()))
                    event.getEntity().setCustomName("Vampiro Enderman");
                if (event.getEntityType() == EntityType.SLIME &&
                        mundos.isOverworld(event.getEntity()))
                    event.getEntity().setCustomName("Slime Corrosivo");
                if (event.getEntityType() == EntityType.MAGMA_CUBE &&
                        mundos.isNether(event.getEntity()))
                    event.getEntity().setCustomName("Magma Fire");
                if (event.getEntityType() == EntityType.SPIDER ||
                        event.getEntityType() == EntityType.CAVE_SPIDER)
                    applyRandomEffect(event.getEntity());
                if (event.getEntityType() == EntityType.SKELETON &&
                        mundos.isNether(event.getEntity())) {
                    event.getEntity().getLocation().getWorld().spawnEntity(event.getLocation(),
                            EntityType.WITCH);
                    event.setCancelled(true);
                }
                if (event.getEntityType() == EntityType.ENDERMAN &&
                        mundos.isNether(event.getEntity())) {
                    Creeper creeper = (Creeper) event.getLocation().getWorld().spawnEntity(event.getLocation(),
                            EntityType.CREEPER);
                    creeper.setPowered(true);
                    event.setCancelled(true);
                }
                if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN && mundos.isNether(event.getEntity())) {
                    PigZombie pigZombie = (PigZombie) event.getEntity();
                    pigZombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                    pigZombie.getEquipment().setHelmetDropChance(0);
                    pigZombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                    pigZombie.getEquipment().setChestplateDropChance(0);
                    pigZombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    pigZombie.getEquipment().setLeggingsDropChance(0);
                    pigZombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    pigZombie.getEquipment().setBootsDropChance(0);

                    pigZombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                    pigZombie.getEquipment().setItemInMainHandDropChance(0);
                }
                if (event.getEntityType() == EntityType.PIGLIN_BRUTE && mundos.isNether(event.getEntity())) {
                    PiglinBrute piglinBrute = (PiglinBrute) event.getEntity();
                    piglinBrute.addPotionEffect(
                            new PotionEffect(Potions.DR, Integer.MAX_VALUE, 0));
                }
            }
        }

        // Dia 10
        if (days >= 10) {
            if (event.getEntityType() == EntityType.SKELETON &&
                    event.getLocation().getWorld().getName().equals(Mundos.WORLD_OVERWORLD)) {
                LivingEntity skeleton = event.getEntity();
                int randomNumber = new Random().nextInt(4);

                Skeleton skeletonMethod = null;
                switch (randomNumber) {
                    case 0:
                        skeletonMethod = skeletonsOp.spawnSkeletonOne(event);
                        break;
                    case 1:
                        skeletonMethod = skeletonsOp.spawnSkeletonTwo(event);
                        break;
                    case 2:
                        skeletonMethod = skeletonsOp.spawnSkeletonThree(event);
                        break;
                    case 3:
                        skeletonMethod = skeletonsOp.spawnSkeletonFour(event);
                        break;
                }

                skeleton = skeletonMethod;
            } else if (event.getEntityType() == EntityType.WOLF) {
                event.getEntity().getLocation()
                        .getWorld().spawnEntity(event.getLocation(), EntityType.RAVAGER);
                event.setCancelled(true);
            } else if (event.getEntityType() == EntityType.CAT && !(days >= 12)) {
                Location location = event.getLocation();
                String mensaje = String.format("&l&5Un Gato Suicida va a explotar en la ubicacion: &4%s %s %s",
                        location.getX(), location.getY(), location.getZ());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', mensaje));
                final Creeper creeper = (Creeper) event.getEntity().getLocation()
                        .getWorld().spawnEntity(event.getLocation(), EntityType.CREEPER);
                Cat cat = (Cat) event.getEntity();
                cat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0));
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0));
                creeper.setCustomName("CreeperEXCat");
                creeper.setCustomNameVisible(false);
                creeper.setSilent(true);
                creeper.setAI(false);
                creeper.setExplosionRadius(100);
                creeper.ignite();
                cat.addPassenger(creeper);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        creeper.ignite();
                    }
                }.runTaskLater(plugin, 400L);

            }
        }

        // Dia 12
        if (days >= 12) {
            if (event.getEntityType() == EntityType.SLIME) {
                if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) {
                    return;
                }

                Slime slime = (Slime) event.getEntity();
                slime.setSize(16);
                slime.setCustomName(ChatColor.GREEN + "Giga Slime Corrosivo");

                int x = random.nextInt(4) + 1;

                double originalMaxHealth = slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newMaxHealth = originalMaxHealth * x; // Multiplicar por x

                slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
            }
        }
        // Dia 14

        // Dia 16

        // Dia 18

        // Dia 20
    }

    private Plugin plugin;
    private final Spiders spiders = new Spiders();
    private final Skeletons skeletons = new Skeletons();
    private final SkeletonsLevelUp skeletonsOp = new SkeletonsLevelUp();
    private final Blazes blazes = new Blazes();
    private final Creepers creepers = new Creepers();
    private final WitherSkeletons witherSkeletons = new WitherSkeletons();
    private final Random random = new Random();
    private Map<Chicken, Silverfish> chickenToSilverfish = new HashMap<>();
    private Set<EntityType> mobsPasives = new HashSet<>();
    private Map<EntityType, Zombie> entityToZombie = new HashMap<>();
    private final Mundos mundos = new Mundos();

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
}
