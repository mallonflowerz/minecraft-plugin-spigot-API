package com.mallonflowerz.spigot.creatures.dia_10;
 
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Message;
import com.mallonflowerz.spigot.statics.Mundos;

import net.md_5.bungee.api.chat.TextComponent;

public class DragonBattle implements Listener {

    private boolean isBattle = false;
    private boolean isPhase1 = true;
    private boolean isPhase2 = false;
    private boolean isPhase3 = false;
    private boolean isPhase4 = false;
    private int countCristal = 4;
    private Location breathAttackLocation = null;
    private final Plugin plugin;
    private final Entities entities = new Entities();
    private final Mundos mundos = new Mundos();
    private final Message message = new Message();
    private final Random random = new Random();

    public DragonBattle(final Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    if (!p.getWorld().getName().equals(Mundos.WORLD_END)) {
                        p.damage(0.5);
                        TextComponent text = new TextComponent(
                                ChatColor.RED + "No puedes estar aqui mientras la batalla del End esta activa!!");
                        p.sendMessage(text.toLegacyText());
                    }
                }
            }
        }, 0L, 20L);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            isBattle = true;
            if (isPhase1) {
                EnderDragon enderDragon = (EnderDragon) event.getEntity();
                TextComponent text = new TextComponent(ChatColor.DARK_AQUA + "Sra Dragona");
                enderDragon.setCustomName(text.toLegacyText());
                message.sendMessage("&6La Fase 1 ha comenzado. A sufrir!!!!");
            } else if (isPhase2) {
                EnderDragon enderDragon = (EnderDragon) event.getEntity();
                TextComponent text = new TextComponent(ChatColor.DARK_PURPLE + "Vintage Sra Dragona");
                enderDragon.setCustomName(text.toLegacyText());
                message.sendMessage("&bLa Fase 2 ha comenzado. Se pone interesante...");
            } else if (isPhase3) {
                EnderDragon enderDragon = (EnderDragon) event.getEntity();
                TextComponent text = new TextComponent(ChatColor.GOLD + "Furious Sra Dragona");
                enderDragon.setCustomName(text.toLegacyText());
                message.sendMessage("&9La Fase 3 ha comenzado. Guau! han sobrevivido.");
            } else if (isPhase4) {
                EnderDragon enderDragon = (EnderDragon) event.getEntity();
                TextComponent text = new TextComponent(ChatColor.DARK_RED + "Revenger Sra Dragona");
                enderDragon.setCustomName(text.toLegacyText());
                message.sendMessage("&cLa Fase Final ha comenzado. &l¡¡El odio de la Dragona esta sobre ustedes!!");
                onWither();
                spawnIronGolems();
            }
        }
    }

    @EventHandler
    public void onEnderDragonPhases(EnderDragonChangePhaseEvent event) {
        EnderDragon enderDragon = event.getEntity();
        EnderDragon.Phase c = event.getCurrentPhase();
        Location l = enderDragon.getLocation();

        if (c == EnderDragon.Phase.CIRCLING) {
            if (isPhase2) {
                entities.onTntPrimed(l);
            } else if (isPhase3) {
                applyEffectRandom();
            }
        } else if (c == EnderDragon.Phase.FLY_TO_PORTAL) {
            if (isPhase2) {
                entities.onTntPrimed(l);
            } else if (isPhase3) {
                spawnMobsRandom();
            }
        } else if (c == EnderDragon.Phase.STRAFING) {
            if (isPhase2 || isPhase3 || isPhase4) {
                entities.onGhast(l);
            }
        } else if (c == EnderDragon.Phase.LAND_ON_PORTAL) {
            if (isPhase2 || isPhase3 || isPhase4) {
                entities.onCreeper(l);
                if (isPhase4) {
                    entities.onWitch(l);
                    entities.onWitherSkeleton(l);
                }
            }
        } else if (c == EnderDragon.Phase.BREATH_ATTACK) {
            if (isPhase2 || isPhase3 || isPhase4) {
                breathAttackLocation = l;
            }
        } else if (c == EnderDragon.Phase.ROAR_BEFORE_ATTACK) {
            if (isPhase2 || isPhase3 || isPhase4) {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.SLOW, 100, 1));
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
                }
                if (isPhase3) {
                    spawnMobsRandom();
                }
            }
        } else if (c == EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET) {
            if (isPhase3) {
                spawnMobsRandom();
            }
        } else if (c == EnderDragon.Phase.HOVER) {
            if (isPhase2 || isPhase3 || isPhase4) {
                entities.onGhast(l);
            }
        } else if (c == EnderDragon.Phase.LEAVE_PORTAL) {
            if (isPhase2 || isPhase3 || isPhase4) {
                TNTPrimed tnt = l.getWorld().spawn(l, TNTPrimed.class);
                tnt.setCustomName("TNTCabum");
                tnt.setCustomNameVisible(false);
                tnt.setFuseTicks(60);
                tnt.setYield(25.0F);
                tnt.setVisibleByDefault(false);
            }
            if (isPhase3) {
                spawnMobsRandom();
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON && isPhase1) {
            isPhase1 = false;
            isPhase2 = true;
            addEnderCrystals();
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON && isPhase2) {
            isPhase2 = false;
            isPhase3 = true;
            addEnderCrystals();
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON && isPhase3) {
            isPhase3 = false;
            isPhase4 = true;
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON && isPhase4) {
            isPhase4 = false;
            isPhase1 = true;
            isBattle = false;
            spawnFireworks(event.getEntity().getLocation());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof EnderCrystal && event.getDamager() instanceof Player &&
                mundos.isEnd(event.getEntity()) && isBattle) {
            EnderCrystal enderCrystal = (EnderCrystal) event.getEntity();
            Location location = enderCrystal.getLocation();
            location.setX(location.getX() - 3);
            location.setY(location.getY() - 8);
            location.setZ(location.getZ() - 3);
            entities.onGhast(location);
        } else if (event.getEntity() instanceof EnderCrystal && event.getDamager() instanceof Arrow &&
                mundos.isEnd(event.getEntity()) && isBattle) {
            Player player = (Player) ((Projectile) event.getDamager()).getShooter();
            if (player != null) {
                EnderCrystal enderCrystal = (EnderCrystal) event.getEntity();
                Location location = enderCrystal.getLocation();
                location.setX(location.getX() - 3);
                location.setY(location.getY() - 8);
                location.setZ(location.getZ() - 3);
                entities.onGhast(location);
            }
        } else if (event.getDamager().getType() == EntityType.ENDER_CRYSTAL &&
                event.getEntityType() == EntityType.GHAST && isBattle) {
            event.setCancelled(true);
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON &&
                event.getDamager().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getDamager();
            Material item = player.getInventory().getItemInMainHand().getType();
            if (random.nextInt(100) + 1 <= 10 && isPhase2) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.LEVITATION, 200, 9));
            } else if (random.nextInt(100) + 1 <= 25 && isPhase3) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.LEVITATION, 200, 9));
            } else if (random.nextInt(100) + 1 <= 35 && isPhase3) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.LEVITATION, 200, 9));
            }
            if (itemsNetherite.contains(item) && !isPhase1) {
                event.setCancelled(true);
            } else if (itemsDiamond.contains(item) && !isPhase2) {
                event.setCancelled(true);
            } else if (itemsIron.contains(item) ||
                    itemsGolden.contains(item) && !isPhase3) {
                event.setCancelled(true);
            }
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON &&
                event.getDamager().getType() == EntityType.ARROW) {
            Player player = (Player) ((Projectile) event.getDamager()).getShooter();
            if (!(random.nextInt(100) + 1 <= 60) && isPhase2 && player != null) {
                event.setCancelled(true);
            } else if (!(random.nextInt(100) + 1 <= 40) && isPhase3 && player != null) {
                event.setCancelled(true);
            } else if (!(random.nextInt(100) + 1 <= 20) && isPhase4 && player != null) {
                player.damage(4.0);
                event.setCancelled(true);
            }
        } else if (event.getEntityType() == EntityType.ENDER_CRYSTAL &&
                event.getDamager().getType() == EntityType.PRIMED_TNT ||
                event.getDamager().getType() == EntityType.CREEPER ||
                event.getDamager().getType() == EntityType.FIREBALL && isBattle) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTPrimed(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.PRIMED_TNT &&
                mundos.isEnd(event.getEntity()) && isBattle
                && event.getEntity().getCustomName() != null
                && event.getEntity().getCustomName().equals("TNTCabum")) {
            event.blockList().clear();
            event.setYield(20.0F);
        } else if (mundos.isEnd(event.getEntity())) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON && breathAttackLocation != null &&
                isBattle && mundos.isEnd(event.getEntity())) {
            Block block = event.getBlock();
            Location blockLocation = block.getLocation();

            if (block.getType() == Material.AIR
                    && blockLocation.equals(breathAttackLocation.getBlock().getLocation())) {
                // Spawneo de la TNT en la ubicación donde cayó el aliento
                TNTPrimed tnt = block.getWorld().spawn(blockLocation.add(0.5, 0.5, 0.5), TNTPrimed.class);
                tnt.setFuseTicks(0); // Ajusta el tiempo de explosión según lo necesites
                breathAttackLocation = null; // Reiniciar la ubicación después de spawnear la TNT
                tnt.setVisibleByDefault(false);
                tnt.setYield(18.0F);
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getEntity().getShooter() instanceof Witch &&
                mundos.isEnd(event.getEntity())) {
            // Agregar efecto de daño instantáneo al lanzar la poción.
            for (Entity entity : event.getAffectedEntities()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 0));
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof EnderDragon) {

            // Evitar daño por explosiones
            if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                    event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                event.setCancelled(true);
                return;
            }

            // Evitar daño de endermans y otros mobs
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
                if (damageByEntityEvent.getDamager() instanceof Player ||
                        damageByEntityEvent.getDamager() instanceof Arrow) {
                    // Permitir daño causado por jugadores
                    return;
                } else {
                    event.setCancelled(true);
                    return;
                }
            }

            // Asegurarse de que el dragón siempre es vulnerable al daño de jugadores
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
                if (damageByEntityEvent.getDamager() instanceof Player ||
                        damageByEntityEvent.getDamager() instanceof Arrow) {
                    // Permitir daño causado por jugadores
                    return;
                }
            }

            // Otros tipos de daño que no se manejan aquí
        }
    }

    private void spawnIronGolems() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Location location = player.getLocation();
            location.setX(location.getX() + 2);
            location.setZ(location.getZ() + 2);
            location.getWorld().spawnEntity(location, EntityType.IRON_GOLEM);
        }
    }

    private void onWither() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            Location location = p.getLocation();
            Wither wither = (Wither) location.getWorld().spawnEntity(location, EntityType.WITHER);
            TextComponent text = new TextComponent(ChatColor.DARK_RED + "Wither Revenger");
            wither.setCustomName(text.toLegacyText());
            wither.setTarget(p.getPlayer());
        }
    }

    private void spawnMobsRandom() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Location location = player.getLocation();
            location.setX(location.getX() + 2);
            location.setZ(location.getZ() + 2);
            switch (random.nextInt(4)) {
                case 0:
                    location.getWorld().spawnEntity(location, EntityType.VINDICATOR);
                    break;
                case 1:
                    location.getWorld().spawnEntity(location, EntityType.RAVAGER);
                    break;
                case 2:
                    location.getWorld().spawnEntity(location, EntityType.EVOKER);
                    break;
                case 3:
                    location.getWorld().spawnEntity(location, EntityType.BLAZE);
                    break;
                case 4:
                    location.getWorld().spawnEntity(location, EntityType.VEX);
                    break;
            }

        }
    }

    private void spawnFireworks(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // Configurar efectos y colores del fuego artificial
        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.RED, Color.YELLOW, Color.ORANGE)
                .with(Type.BALL_LARGE)
                .trail(true)
                .flicker(true)
                .build();

        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(20); // Potencia del fuego artificial

        firework.setFireworkMeta(fireworkMeta);
    }

    private List<Material> itemsNetherite = Arrays.asList(
            Material.NETHERITE_AXE,
            Material.NETHERITE_HOE,
            Material.NETHERITE_SWORD,
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_SHOVEL);

    private List<Material> itemsDiamond = Arrays.asList(
            Material.DIAMOND_AXE,
            Material.DIAMOND_HOE,
            Material.DIAMOND_SWORD,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_SHOVEL);

    private List<Material> itemsIron = Arrays.asList(
            Material.IRON_AXE,
            Material.IRON_HOE,
            Material.IRON_SWORD,
            Material.IRON_PICKAXE,
            Material.IRON_SHOVEL);

    private List<Material> itemsGolden = Arrays.asList(
            Material.GOLDEN_AXE,
            Material.GOLDEN_HOE,
            Material.GOLDEN_SWORD,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_SHOVEL);

    private void addEnderCrystals() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (countCristal > 0) {
                ItemStack item = new ItemStack(Material.END_CRYSTAL, 1);
                if (player.getInventory().firstEmpty() > -1) {
                    player.getInventory().addItem(item);
                    countCristal--;
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                    countCristal--;
                }

            }
        }
    }

    private void applyEffectRandom() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            int effect = random.nextInt(7);
            int time = (random.nextInt(4) + 1) * 1200;
            int level = random.nextInt(4);

            switch (effect) {
                case 0:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.SLOW, time, level));
                    break;
                case 1:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.WEAKNESS, time, level));
                    break;
                case 2:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.DARKNESS, time, level));
                    break;
                case 3:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.HUNGER, time, level));
                    break;
                case 4:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.LEVITATION, time, level));
                    break;
                case 5:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.POISON, time, level));
                    break;
                case 6:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.WITHER, time, level));
                    break;
                case 7:
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.CONFUSION, time, level));
                    break;
            }

        }
    }
}
