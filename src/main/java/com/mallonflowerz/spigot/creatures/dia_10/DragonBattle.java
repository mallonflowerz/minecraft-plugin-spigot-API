package com.mallonflowerz.spigot.creatures.dia_10;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
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
    private Location breathAttackLocation = null;
    private final Plugin plugin;
    private final Entities entities = new Entities();
    private final Mundos mundos = new Mundos();
    private final Message message = new Message();
    private final List<EnderCrystal> respawEnderCrystals = new ArrayList<>();

    public DragonBattle(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            EnderDragon enderDragon = (EnderDragon) event.getEntity();
            TextComponent text = new TextComponent(ChatColor.DARK_PURPLE + "Sra Dragon");
            enderDragon.addPotionEffect(
                    new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 50));
            enderDragon.setCustomName(text.toLegacyText());
            isBattle = true;
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

        if (c == EnderDragon.Phase.CIRCLING) {
            if (isPhase2) {
                entities.onTntPrimed(enderDragon.getLocation());
            }
        } else if (c == EnderDragon.Phase.FLY_TO_PORTAL) {
            if (isPhase2) {
                entities.onTntPrimed(enderDragon.getLocation());
            }
        } else if (c == EnderDragon.Phase.STRAFING) {
            if (isPhase2) {
                entities.onGhast(enderDragon.getLocation());
            }
        } else if (c == EnderDragon.Phase.LAND_ON_PORTAL) {
            if (isPhase2) {
                entities.onCreeper(enderDragon.getLocation());
            }
        } else if (c == EnderDragon.Phase.BREATH_ATTACK) {
            if (isPhase2) {
                breathAttackLocation = enderDragon.getLocation();
            }
        } else if (c == EnderDragon.Phase.ROAR_BEFORE_ATTACK) {
            if (isPhase2) {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.SLOW, 100, 1));
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
                }
            }
        } else if (c == EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET) {
            if (isPhase2) {
                message.sendMessage(
                        String.format("&1La Sra Dragon esta a punto de tirar su aliento a cualquier jugador"));
            }
        } else if (c == EnderDragon.Phase.LEAVE_PORTAL) {
            if (isPhase2) {
                TNTPrimed tnt = enderDragon.getLocation().getWorld().spawn(enderDragon.getLocation(), TNTPrimed.class);
                tnt.setCustomName("TNTCabum");
                tnt.setCustomNameVisible(false);
                tnt.setFuseTicks(60);
                tnt.setVisibleByDefault(false);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON && isBattle) {
            EnderDragon enderDragon = (EnderDragon) event.getEntity();
            double currentHealth = enderDragon.getHealth();
            double maxHealth = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if (currentHealth <= maxHealth * 1.0) {
                isPhase1 = true;
            } else if (currentHealth < maxHealth * 0.75) {
                isPhase1 = false;
                isPhase2 = true;
            } else if (currentHealth < maxHealth * 0.50) {
                isPhase2 = false;
                isPhase3 = true;
            } else if (currentHealth < maxHealth * 0.25) {
                isPhase3 = false;
                isPhase4 = true;
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
        }
    }

    @EventHandler
    public void onTNTPrimed(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.PRIMED_TNT &&
                mundos.isEnd(event.getEntity()) && isBattle
                && event.getEntity().getCustomName() != null
                && event.getEntity().getCustomName().equals("TNTCabum")) {
            event.blockList().clear();
            event.setYield(30.0F);
        } else {
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
}
