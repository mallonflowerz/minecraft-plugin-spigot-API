package com.mallonflowerz.spigot.creatures.dia_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Message;
import com.mallonflowerz.spigot.statics.Mundos;

import net.md_5.bungee.api.chat.TextComponent;

public class DragonBattle implements Listener {

    private boolean isBattle = false;
    private boolean isPhase1 = false;
    private boolean isPhase2 = false;
    private boolean isPhase3 = false;
    private boolean isPhase4 = false;
    private boolean spawnFinal = false;
    private boolean isSend = false;
    private Location breathAttackLocation = null;
    private final Plugin plugin;
    private final Entities entities = new Entities();
    private final Mundos mundos = new Mundos();
    private final Message message = new Message();
    private final Random random = new Random();
    private final List<EnderCrystal> respawEnderCrystals = new ArrayList<>();

    public DragonBattle(Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            EnderDragon enderDragon = (EnderDragon) event.getEntity();
            TextComponent text = new TextComponent(ChatColor.DARK_PURPLE + "Sra Dragona");
            enderDragon.setCustomName(text.toLegacyText());
            isBattle = true;
            message.sendMessage("&6La Fase 1 ha comenzado. A sufrir!!!!");
            isPhase1 = true;
        } else if (event.getEntityType() == EntityType.ENDER_CRYSTAL &&
                mundos.isEnd(event.getEntity())) {
            EnderCrystal enderCrystal = (EnderCrystal) event.getEntity();
            Block block = enderCrystal.getLocation().getBlock();
            if (block.getType() == Material.BEDROCK) {
                // Obtener la ubicación del End Crystal
                respawEnderCrystals.add(enderCrystal);
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
            message.sendMessage(
                    String.format("&1La Sra Dragona esta a punto de tirar su aliento a cualquier jugador"));
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
                tnt.setVisibleByDefault(false);
            }
            if (isPhase3) {
                spawnMobsRandom();
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON && isBattle) {
            EnderDragon enderDragon = (EnderDragon) event.getEntity();
            double currentHealth = enderDragon.getHealth();
            double maxHealth = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            if (currentHealth < maxHealth * 0.75) {
                isSend = false;
                isPhase1 = false;
                isPhase2 = true;
                if (!isSend) {
                    message.sendMessage("&bLa Fase 2 ha comenzado. Se pone interesante...");
                    isSend = true;
                }

            } else if (currentHealth < maxHealth * 0.50) {
                isSend = false;
                isPhase2 = false;
                isPhase3 = true;
                if (!isSend) {
                    message.sendMessage("&9La Fase 3 ha comenzado. Guau! han sobrevivido.");
                    isSend = true;
                }

                respawEnderCrystals();
            } else if (currentHealth < maxHealth * 0.25) {
                isSend = false;
                isPhase3 = false;
                isPhase4 = true;

                if (!isSend) {
                    message.sendMessage("&cLa Fase Final ha comenzado. &l¡¡El odio de la Dragona esta sobre ustedes!!");
                    isSend = true;
                }
                if (!spawnFinal) {
                    respawEnderCrystals();
                    entities.onWither(enderDragon.getLocation());
                    entities.onWither(enderDragon.getLocation());
                    spawnIronGolems();
                }
                spawnFinal = true;
                if (currentHealth == maxHealth * 0.00) {
                    isBattle = false;
                    message.sendMessage("&c¡¡Felicidades!! Lograron vencer a la Dragona. Afortunados!!");
                    spawnFireworks(enderDragon.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof EnderCrystal && event.getDamager() instanceof Player &&
                mundos.isEnd(event.getEntity()) && isPhase1 && isBattle) {
            EnderCrystal enderCrystal = (EnderCrystal) event.getEntity();
            Location location = enderCrystal.getLocation();
            location.setX(location.getX() - 3);
            location.setY(location.getY() - 8);
            location.setZ(location.getZ() - 3);
            entities.onGhast(location);
        } else if (event.getDamager().getType() == EntityType.ENDER_CRYSTAL &&
                event.getEntityType() == EntityType.GHAST && isBattle) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTPrimed(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.PRIMED_TNT &&
                mundos.isEnd(event.getEntity()) && isBattle
                && event.getEntity().getCustomName() != null
                && event.getEntity().getCustomName().equals("TNTCabum")) {
            // event.blockList().clear();
            event.setYield(30.0F);
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

    private void spawnIronGolems() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Location location = player.getLocation();
            location.setX(location.getX() + 2);
            location.setZ(location.getZ() + 2);
            location.getWorld().spawnEntity(location, EntityType.IRON_GOLEM);
        }
    }

    private void respawEnderCrystals() {
        for (EnderCrystal e : respawEnderCrystals) {
            e.getWorld().spawn(e.getLocation(), EnderCrystal.class);
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

    private void applyEffectRandom() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            int effect = random.nextInt(7);
            int time = (random.nextInt(8) + 1) * 1200;
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
