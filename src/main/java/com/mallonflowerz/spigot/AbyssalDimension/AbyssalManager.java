package com.mallonflowerz.spigot.AbyssalDimension;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.persistence.PersistentDataType;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.AbyssalDimension.generator.AbyssalGenerator;
import com.mallonflowerz.spigot.data.AbyssalDataManager;

import lombok.Getter;

public class AbyssalManager implements Listener {

    private Plugin main;
    private World beginningWorld;

    private AbyssalDataManager data;
    @Getter
    private boolean closed = false;

    public AbyssalManager(Plugin main) {
        this.main = main;
        this.beginningWorld = null;
        this.data = main.getBeData();

        if (main.getDays() >= 14) {
            generateWorld();
            main.getServer().getPluginManager().registerEvents(this, main);
        }
    }

    public World getBeginningWorld() {
        return beginningWorld;
    }

    private void generateWorld() {

        if (Bukkit.getWorld("pdc_the_beginning") == null) {
            WorldCreator creator = new WorldCreator("pdc_the_beginning");
            creator.environment(World.Environment.THE_END);
            creator.generator(new AbyssalGenerator());
            creator.generateStructures(false);
            this.beginningWorld = creator.createWorld();
            if (main.getConfig().getBoolean("Toggles.Doble-Mob-Cap")) {
                beginningWorld.setSpawnLimit(SpawnCategory.MONSTER, 140);
            }
            beginningWorld.setGameRule(GameRule.MOB_GRIEFING, false);
        } else {
            this.beginningWorld = Bukkit.getWorld("pdc_the_beginning");
        }
    }

    public void closeBeginning() {
        if (beginningWorld == null)
            return;
        for (Player p : beginningWorld.getPlayers()) {
            p.teleport(main.world.getSpawnLocation());
            p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1.0F, 1.0F);
        }
        Bukkit.broadcastMessage("&eThe Beginning ha cerrado temporalmente (DeathTrain).");
        this.closed = true;
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        if (Plugin.isRunningPaperSpigot()) {
            return;
        }
        Player p = e.getPlayer();
        if (e.getCause() != PlayerTeleportEvent.TeleportCause.END_GATEWAY)
            return;
        if (p.getWorld().getName().equalsIgnoreCase(main.world.getName())) {
            try {
                e.getClass().getDeclaredMethod("setCanCreatePortal", Boolean.class).invoke(e, false);
            } catch (Exception x) {
            }
        }

        if (p.getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            if (p.getLocation().getBlock().getState() instanceof EndGateway) {
                EndGateway gateway = (EndGateway) p.getLocation().getBlock().getState();
                gateway.setExitLocation(null);
                gateway.update();
                p.getLocation().getBlock().getState().update();
            }
            try {
                e.getClass().getDeclaredMethod("setCanCreatePortal", Boolean.class).invoke(e, false);
            } catch (Exception x) {
            }
        }
    }

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {

        Player p = e.getPlayer();

        if (e.getCause() != PlayerTeleportEvent.TeleportCause.END_GATEWAY)
            return;

        if (isClosed()) {
            e.setCancelled(true);
            return;
        }

        if (main.getDays() < 50) {
            if (e.getPlayer().getWorld().getName().equalsIgnoreCase(main.world.getName())
                    || e.getPlayer().getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
                e.getPlayer().setNoDamageTicks(e.getPlayer().getMaximumNoDamageTicks());
                e.getPlayer().damage(e.getPlayer().getHealth() + 1.0D, null);
                e.getPlayer().setNoDamageTicks(0);
                Bukkit.broadcastMessage("&c&lEl jugador &4&l" + e.getPlayer().getName()
                        + " &c&lentró a TheBeginning antes de tiempo.");
            }
            return;
        }

        if (p.getWorld().getName().equalsIgnoreCase(main.world.getName())) {

            e.getPlayer().sendMessage("&eBienvenido a The Beginning.");
            e.getPlayer().teleport(beginningWorld.getSpawnLocation());
            e.setCancelled(true);

            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().teleport(beginningWorld.getSpawnLocation());
                }
            }, 20L);
        }

        int x = (int) p.getLocation().getX();
        int z = (int) p.getLocation().getZ();
        if (p.getWorld().getName().equalsIgnoreCase(beginningWorld.getName()) && x != 200 && z != 200) {

            if (p.getLocation().getBlock().getState() instanceof EndGateway) {

                EndGateway gateway = (EndGateway) p.getLocation().getBlock().getState();
                gateway.setExitLocation(null);
                gateway.update();
                p.getLocation().getBlock().getState().update();
            }

            e.getPlayer().teleport(main.world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            if (e.getBlock().getState() instanceof Chest) {
                Chest chest = (Chest) e.getBlock().getState();
                populateChest(chest);
            }
        }
        // if (e.getBlock().getType() == Material.SPAWNER && main.getNetheriteBlock() !=
        // null) {
        // if (main.getDays() < 60) {
        // main.getNetheriteBlock().onBlockBreak(e);
        // }
        // }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getClickedBlock() != null && p.getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getState() instanceof Chest) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    populateChest(chest);
                }
            }
        }

        // if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null
        // && main.getNetheriteBlock() != null) {
        // if (e.getClickedBlock().getType() == Material.CHEST)
        // return;
        // ItemStack ih = e.getPlayer().getInventory().getItemInMainHand();
        // if (ih.getType() == Material.DIAMOND && ih.getItemMeta().isUnbreakable()) {
        // main.getNetheriteBlock().placeCustomBlock(
        // main.getNetheriteBlock().blockFaceToLocation(e.getClickedBlock(),
        // e.getBlockFace()));
        // if (ih.getAmount() > 0) {
        // ih.setAmount(ih.getAmount() - 1);
        // } else {
        // ih = null;
        // }
        // ItemStack finalIh = ih;
        // Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
        // e.getPlayer().getInventory().setItemInMainHand(finalIh);
        // e.getPlayer().updateInventory();
        // });
        // e.setCancelled(true);
        // }
        // }
    }

    @EventHandler
    public void onCreatePortal(PortalCreateEvent e) {
        if (e.getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            for (BlockState s : e.getBlocks()) {
                Block b = s.getBlock();
                if (b.getType() == Material.END_GATEWAY || b.getType() == Material.BEDROCK || s instanceof EndGateway) {
                    if (b.getChunk().getX() == 0 && b.getChunk().getZ() == 0) {
                        e.getBlocks().remove(s);
                        s.setType(Material.AIR);
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        if (beginningWorld == null)
            return;
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWater(BlockDispenseEvent e) {
        if (beginningWorld == null)
            return;
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.BUCKET || e.getItem().getType() == Material.WATER_BUCKET) {
                if (e.getBlock().getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {

        if (beginningWorld == null)
            return;
        if (e.isCancelled() || e.getSpawner() == null)
            return;
        CreatureSpawner spawner = e.getSpawner();
        if (e.getEntity().getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            if (e.getEntityType() != EntityType.ARMOR_STAND) {
                spawner.setSpawnedType(e.getEntityType());
                spawner.update();
            }
            if (e.getEntityType() == EntityType.WITHER) {
                Wither w = (Wither) e.getEntity();
                w.setRemoveWhenFarAway(true);
            }
            // if (e.getEntityType() == EntityType.GHAST) {

            // Ghast g = (Ghast)
            // main.getNmsHandler().spawnCustomGhast(e.getLocation().add(0, 5, 0),
            // CreatureSpawnEvent.SpawnReason.CUSTOM, true);
            // g.setCustomName(TextUtils.format("&6Ender Ghast Definitivo"));
            // main.getNmsAccessor().setMaxHealth(g, 150.0D, true);
            // e.setCancelled(true);
            // }
            if (e.getEntityType() == EntityType.CREEPER) {

                e.getEntity().setCustomName("&6Quantum Creeper");
                ((Creeper) e.getEntity()).setPowered(true);
                e.getEntity().getPersistentDataContainer().set(new NamespacedKey(main, "quantum_creeper"),
                        PersistentDataType.BYTE, (byte) 1);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (beginningWorld == null)
            return;

        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(beginningWorld.getName())) {
            if (e.getBlock().getState() instanceof Chest) {
                AbyssalDataManager ma = main.getBeData();
                ma.addPopulatedChest(e.getBlock().getLocation());
            }
        }
    }

    private void populateChest(Chest chest) {

        if (data.getConfig().contains("PopulatedChests")) {
            if (data.hasPopulatedChest(chest.getLocation()))
                return;
            // if (main.getDays() < 60) {
            // new BeginningLootTable(this).populateChest(chest);
            // }
            data.addPopulatedChest(chest.getLocation());
        }
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void generatePortal(boolean overworld, Location location) {
        WorldEditPortal.generatePortal(overworld, location);
    }
}
