package com.mallonflowerz.spigot.days;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.creatures.dia_10.SkeletonsLevelUp;
import com.mallonflowerz.spigot.statics.Mundos;

public class Dia_10 implements Listener {

    private final Plugin plugin;
    private final Mundos mundos = new Mundos();
    private final Random random = new Random();

    public Dia_10(Plugin plugin) {
        this.plugin = plugin;
    }

    private SkeletonsLevelUp skeletons = new SkeletonsLevelUp();
    private boolean isRaining = false;

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
        } else if (event.getEntityType() == EntityType.WOLF) {
            event.getEntity().getLocation()
                    .getWorld().spawnEntity(event.getLocation(), EntityType.RAVAGER);
            event.setCancelled(true);
        } else if (event.getEntityType() == EntityType.CAT) {
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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.getLocation().getWorld().getTime() < 13000 &&
                itemsBlocked.contains(block.getType())) {
            event.setCancelled(true);
        } else {
            player.damage(1);
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

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Player player = event.getPlayer();
            if (random.nextInt(100) == 0) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.BAD_OMEN, 10, 3));
            }
        }
    }

    @EventHandler
    public void on(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_APPLE &&
                event.getItem().getItemMeta().getDisplayName().equals("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+")) {
            Player player = (Player) event.getPlayer();
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.HEALTH_BOOST, 24000, 1));
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 24000, 0));
        } else if (event.getItem().getType() == Material.GOLDEN_APPLE &&
                event.getItem().getItemMeta().getDisplayName()
                        .equals("\u00A76\u00A7lGolden Apple\u00A75\u00A7l+ Max")) {
            Player player = (Player) event.getPlayer();
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 1));
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0));
        }
        if (foodInstaKill.contains(event.getItem().getType())) {
            event.getPlayer().addPotionEffect(
                    new PotionEffect(PotionEffectType.HARM, 1, 89));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByBlockEvent event) {
        if (event.getEntityType() == EntityType.PLAYER &&
                event.getDamager().getType() == Material.CACTUS) {
            Player player = (Player) event.getEntity();
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.HARM, 1, 100));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && bedTypes.contains(clickedBlock.getType())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                    event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (random.nextInt(100) < 30) {
                    TNTPrimed tnt = clickedBlock.getWorld().spawn(clickedBlock.getLocation(), TNTPrimed.class);
                    tnt.setFuseTicks(0);
                    tnt.setVisibleByDefault(false);
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper && event.getEntity().getCustomName() != null &&
                event.getEntity().getCustomName().equals("CreeperEXCat")) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER &&
                event.getDamager().getType() == EntityType.ARROW) {
            Skeleton skeleton = (Skeleton) ((Projectile) event.getDamager()).getShooter();
            if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 1+")) {
                event.setDamage(16);
            } else if (skeleton.getCustomName() != null && skeleton.getCustomName().equals("Skeleton Level 2+")) {
                event.setDamage(20);
            }
        } else if (event.getEntityType() == EntityType.PLAYER &&
                (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null &&
                        event.getDamager().getCustomName().equals("Skeleton Level 3+"))) {
            event.setDamage(24);
        } else if (event.getEntityType() == EntityType.PLAYER &&
                (event.getDamager().getType() == EntityType.SKELETON && event.getDamager().getCustomName() != null &&
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

    public void onRainingAcid(Player player) {
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

    private final List<Material> bedTypes = Arrays.asList(
            Material.RED_BED,
            Material.BLACK_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.CYAN_BED,
            Material.GRAY_BED,
            Material.GREEN_BED,
            Material.LIGHT_BLUE_BED,
            Material.LIGHT_GRAY_BED,
            Material.LIME_BED,
            Material.MAGENTA_BED,
            Material.ORANGE_BED,
            Material.PINK_BED,
            Material.PURPLE_BED,
            Material.WHITE_BED,
            Material.YELLOW_BED);

    private final List<Material> foodInstaKill = Arrays.asList(
            Material.COOKED_BEEF,
            Material.COOKED_CHICKEN,
            Material.COOKED_PORKCHOP,
            Material.COOKED_MUTTON,
            Material.COOKED_BEEF,
            Material.BEEF,
            Material.MUTTON,
            Material.PORKCHOP,
            Material.CHICKEN);

    private final List<Material> itemsBlocked = Arrays.asList(
            // Madera (log y planks)
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.ACACIA_PLANKS,
            Material.BIRCH_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.OAK_PLANKS,
            Material.SPRUCE_PLANKS,
            // Piedra
            Material.STONE,
            // Minerales (ores)
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.ANCIENT_DEBRIS,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE,
            // Madera del Nether (Nether Logs y Nether Planks)
            Material.CRIMSON_STEM,
            Material.WARPED_STEM,
            Material.CRIMSON_PLANKS,
            Material.WARPED_PLANKS);
}
