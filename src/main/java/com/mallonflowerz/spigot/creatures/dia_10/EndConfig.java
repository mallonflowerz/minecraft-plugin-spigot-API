package com.mallonflowerz.spigot.creatures.dia_10;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.statics.Mundos;

public class EndConfig implements Listener {

    private final Plugin plugin;
    private final Entities entities = new Entities();
    private final Mundos mundos = new Mundos();
    private final Random random = new Random();

    public EndConfig(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM &&
                mundos.isEnd(event.getEntity())) {
            IronGolem ironGolem = (IronGolem) event.getEntity();
            ironGolem.addPotionEffect(
                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 1, true, false));
            ironGolem.addPotionEffect(
                    new PotionEffect(PotionEffectType.SPEED, -1, 3));
            ironGolem.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 3, true, false));
            ironGolem.addPotionEffect(
                    new PotionEffect(PotionEffectType.REGENERATION, -1, 1, true, false));
        } else if (event.getEntityType() == EntityType.ENDERMAN &&
                mundos.isEnd(event.getEntity())) {
            Enderman enderman = (Enderman) event.getEntity();
            enderman.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0, true, false));
            enderman.addPotionEffect(
                    new PotionEffect(PotionEffectType.REGENERATION, -1, 2, true, false));
        } else if (event.getEntityType() == EntityType.SHULKER &&
                mundos.isEnd(event.getEntity())) {
            Shulker shulker = (Shulker) event.getEntity();
            shulker.setCustomName("Shulker TNT");
            shulker.addPotionEffect(
                    new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 0, true, false));
        } else if (event.getEntityType() == EntityType.ENDERMAN &&
                mundos.isEnd(event.getEntity())) {
            if (random.nextInt(100) < 10) {
                entities.onGhast(event.getLocation());
            }
            if (random.nextInt(100) < 10) {
                entities.onCreeper(event.getLocation());
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.SHULKER &&
                mundos.isEnd(event.getEntity())) {
            Shulker shulker = (Shulker) event.getEntity();
            TNTPrimed tnt = shulker.getLocation().getWorld().spawn(shulker.getLocation(), TNTPrimed.class);
            tnt.setFireTicks(60);
            if (random.nextInt(100) > 30) {
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Item) {
            Item item = (Item) event.getEntity();
            if (item.getItemStack().getType() == Material.SHULKER_SHELL) {
                if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER &&
                event.getDamager().getType() == EntityType.SHULKER_BULLET) {
            TNTPrimed tnt = event.getEntity().getLocation().getWorld().spawn(event.getEntity().getLocation(),
                    TNTPrimed.class);
            tnt.setFireTicks(0);
            tnt.setVisibleByDefault(false);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER &&
                event.getEntity().getCustomName() != null && mundos.isEnd(event.getEntity()) &&
                event.getEntity().getCustomName().equals("Creeper Megalodon")) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            creeper.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            event.blockList().clear();
        } else if (event.getEntityType() == EntityType.FIREBALL && mundos.isEnd(event.getEntity()) &&
                event.getEntity() instanceof LargeFireball) {
            LargeFireball fireball = (LargeFireball) event.getEntity();
            if (fireball.getShooter() instanceof Ghast) {
                event.blockList().clear();
                event.setYield(10.0F);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null &&
                event.getClickedBlock().getType() == Material.CHEST &&
                mundos.isEnd(event.getPlayer())) {
            Chest chest = (Chest) event.getClickedBlock().getState();
            populateChest(chest, event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.getWorld().getName().equals(Mundos.WORLD_END) && block.getWorld().getName().equals(Mundos.WORLD_END)
                && block.getType() == Material.CHEST) {
            Chest chest = (Chest) block.getState();
            populateChest(chest, player);
        }
    }

    private boolean hasPlayerInteracted(Block block, String playerName) {
        BlockState blockState = block.getState();
        List<MetadataValue> metadataValues = blockState.getMetadata(playerName);
        for (MetadataValue metadataValue : metadataValues) {
            if (metadataValue.asBoolean()) {
                return true;
            }
        }
        return false;
    }

    private void setPlayerInteracted(Block block, String playerName) {
        BlockState blockState = block.getState();
        blockState.setMetadata(playerName, new FixedMetadataValue(plugin, true));
    }

    private void populateChest(Chest chest, Player player) {
        if (hasPlayerInteracted(chest.getBlock(), player.getName()))
            return;
        setPlayerInteracted(chest.getBlock(), player.getName());
    }
}
