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
import org.bukkit.block.Biome;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.creatures.RavagerWithWitch;
import com.mallonflowerz.spigot.creatures.dia_10.SkeletonsLevelUp;
import com.mallonflowerz.spigot.creatures.dia_4.Spiders;
import com.mallonflowerz.spigot.creatures.dia_6.Blazes;
import com.mallonflowerz.spigot.creatures.dia_6.Creepers;
import com.mallonflowerz.spigot.creatures.dia_6.Skeletons;
import com.mallonflowerz.spigot.creatures.dia_6.WitherSkeletons;
import com.mallonflowerz.spigot.items.Bow;
import com.mallonflowerz.spigot.items.DefinitiveArmor;
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
            } else if (event.getEntityType() == EntityType.WARDEN && days < 12) {
                event.setCancelled(true);
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
                Slime slime = (Slime) event.getEntity();
                slime.getEquipment().setItemInMainHand(DefinitiveArmor.craftDefinitiveHelmet());
                slime.getEquipment().setItemInMainHandDropChance(0.5F);
                if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) {
                    return;
                }
                slime.setSize(16);
                slime.setCustomName(ChatColor.GREEN + "Giga Slime Corrosivo");
                int x = random.nextInt(4) + 1;

                double originalMaxHealth = slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newMaxHealth = originalMaxHealth * x; // Multiplicar por x

                slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
            } else if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN &&
                    mundos.isNether(event.getEntity())) {
                event.getEntity().getWorld().spawn(event.getLocation(), Piglin.class);
                event.setCancelled(true);
            } else if (event.getEntityType() == EntityType.GHAST &&
                    mundos.isNether(event.getEntity())) {
                Ghast ghast = (Ghast) event.getEntity();
                addRandomPotions(ghast);
                ghast.setCustomName(ChatColor.AQUA + "Ghast Impredecible");
                ghast.getEquipment().setItemInMainHand(DefinitiveArmor.craftDefinitiveChest());
                ghast.getEquipment().setItemInMainHandDropChance(2.0F);

                int x = random.nextInt(4) + 1;
                double originalMaxHealth = ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newMaxHealth = originalMaxHealth * x;
                ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
            } else if (event.getEntityType() == EntityType.CREEPER &&
                    mundos.isOverworld(event.getEntity())) {
                if (event.getEntity().getCustomName() == null) {
                    Creeper creeper = (Creeper) event.getEntity();
                    creeper.setPowered(true);
                    creeper.setInvisible(true);
                }
            } else if (event.getEntityType() == EntityType.WARDEN &&
                    mundos.isOverworld(event.getEntity())) {
                Warden warden = (Warden) event.getEntity();
                warden.addPotionEffect(
                        new PotionEffect(PotionEffectType.REGENERATION, -1, 1));
                warden.getEquipment().setItemInMainHand(DefinitiveArmor.craftDefinitiveLeggings());
                warden.getEquipment().setItemInMainHandDropChance(2.0F);
                if (random.nextInt(100) + 1 <= 20) {
                    Wither wither = (Wither) warden.getWorld()
                            .spawnEntity(warden.getLocation(), EntityType.WITHER);
                    wither.getEquipment().setItemInMainHand(DefinitiveArmor.craftDefinitiveLeggings());
                    wither.getEquipment().setItemInMainHandDropChance(2.0F);
                }
            } else if (event.getEntityType() == EntityType.HOGLIN &&
                    mundos.isNether(event.getEntity()) && days < 14) {
                Hoglin hoglin = (Hoglin) event.getEntity();
                hoglin.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 1));
                hoglin.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_APPLE, random.nextInt(4)));
                hoglin.getEquipment().setItemInMainHandDropChance(1.0F);
            }
        }
        // Dia 14
        if (days >= 14) {
            Location location = event.getLocation();
            if (location.getWorld().getBiome(location) == Biome.PLAINS) {
                if (random.nextInt(100) + 1 <= 2) {
                    location.setX(location.getX() + 2);
                    location.setZ(location.getZ() + 2);
                    CaveSpider brute = location.getWorld().spawn(location, CaveSpider.class);
                    brute.addPotionEffect(
                            new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 9, true, false));
                    brute.setCustomName(ChatColor.BOLD + "Cave Spider OP");
                    int x = random.nextInt(30) + 1;
                    double originalMaxHealth = brute.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    double newMaxHealth = originalMaxHealth * x;
                    brute.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
                    brute.getEquipment().setItemInOffHand(new ItemStack(Material.TORCH));
                    brute.getEquipment().setItemInOffHandDropChance(0F);
                    brute.getEquipment().setBoots(Bow.craftBow());
                    brute.getEquipment().setBootsDropChance(0.5F);
                }
            }
            if (event.getEntityType() == EntityType.COW && mundos.isOverworld(event.getEntity())) {
                Ravager ravager = location.getWorld().spawn(location, Ravager.class);
                ravager.addPassenger(location.getWorld().spawnEntity(location, EntityType.SKELETON));
                event.setCancelled(true);
            } else if (event.getEntityType() == EntityType.PHANTOM && mundos.isOverworld(event.getEntity())) {
                Phantom phantom = (Phantom) event.getEntity();
                phantom.setSize(8);
                double originalMaxHealth = phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newMaxHealth = originalMaxHealth * 2;
                phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
            } else if (event.getEntityType() == EntityType.PIGLIN &&
                    mundos.isNether(event.getEntity())) {
                if (random.nextInt(100) + 1 <= 5) {
                    ravagerWitch.spawnRavager(event.getLocation());
                }
                event.setCancelled(true);
            } else if (event.getEntityType() == EntityType.MAGMA_CUBE && mundos.isNether(event.getEntity())) {
                MagmaCube cube = (MagmaCube) event.getEntity();
                if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) {
                    return;
                }
                cube.setSize(14);
            } else if (event.getEntityType() == EntityType.VINDICATOR &&
                    mundos.isOverworld(event.getEntity())) {
                Vindicator v = (Vindicator) event.getEntity();
                v.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                v.addPotionEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0));
            } else if (event.getEntityType() == EntityType.DROWNED ||
                    event.getEntityType() == EntityType.SQUID || event.getEntityType() == EntityType.GLOW_SQUID) {
                if (event.getEntityType() == EntityType.SQUID || event.getEntityType() == EntityType.GLOW_SQUID) {
                    if (random.nextInt(100) + 1 <= 2) {
                        event.setCancelled(true);
                        event.getLocation().getWorld().spawn(event.getLocation(), ElderGuardian.class);
                    }
                } else {
                    event.setCancelled(true);
                    event.getLocation().getWorld().spawn(event.getLocation(), ElderGuardian.class);
                }

            }
        }
        // Dia 16

        // Dia 18

        // Dia 20
    }

    private Plugin plugin;
    private final Spiders spiders = new Spiders();
    private final Skeletons skeletons = new Skeletons();
    private final SkeletonsLevelUp skeletonsOp = new SkeletonsLevelUp();
    private final Blazes blazes = new Blazes();
    private final RavagerWithWitch ravagerWitch = new RavagerWithWitch();
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

    private void addRandomPotions(LivingEntity entity) {
        PotionEffectType[] effects = {
                PotionEffectType.REGENERATION,
                PotionEffectType.DAMAGE_RESISTANCE,
                PotionEffectType.GLOWING,
                PotionEffectType.HEALTH_BOOST,
                PotionEffectType.INCREASE_DAMAGE
        };

        int maxEffects = 5; // Máximo número de efectos que pueden aplicarse
        int numEffects = random.nextInt(maxEffects) + 1; // Número aleatorio de efectos a aplicar

        for (int i = 0; i < numEffects; i++) {
            PotionEffectType effectType = effects[random.nextInt(effects.length)];
            int effectDuration = random.nextInt(180) + 1 * 1200;
            int effectLevel = random.nextInt(5) + 1; // Nivel entre 1 y 5

            PotionEffect potionEffect = new PotionEffect(effectType, effectDuration, effectLevel);
            entity.addPotionEffect(potionEffect);
        }
    }
}
