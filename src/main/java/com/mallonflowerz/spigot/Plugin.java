package com.mallonflowerz.spigot;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.mallonflowerz.spigot.creatures.dia_10.DragonBattle;
import com.mallonflowerz.spigot.creatures.dia_10.EndConfig;
import com.mallonflowerz.spigot.days.Dia_10;
import com.mallonflowerz.spigot.days.Dia_2;
import com.mallonflowerz.spigot.days.Dia_4;
import com.mallonflowerz.spigot.days.Dia_6;
import com.mallonflowerz.spigot.days.Dia_8;
import com.mallonflowerz.spigot.items.ChestTop;
import com.mallonflowerz.spigot.items.RecipeDia10;
import com.mallonflowerz.spigot.items.RecipeDia6;

public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("plugin-demo");

  // private final ChestTop chestTop = new ChestTop();
  // private RecipeDia6 recipeDia6;
  // private final Dia_6 dia_6 = new Dia_6();
  // private final Dia_8 dia_8 = new Dia_8();
  private Dia_10 dia_10;
  private EndConfig endConfig;
  private DragonBattle dragonBattle;
  private RecipeDia10 recipeDia10;

  public void onEnable() {
    LOGGER.info("PLUGIN DEMO ENABLED");
    // chestTop.spawnChestWithItemsTop(new
    // Location(getServer().getWorld(Mundos.WORLD_OVERWORLD), 0, 90, 0));
    endConfig = new EndConfig(this);
    dragonBattle = new DragonBattle(this);
    // Dia 10
    // dia_10 = new Dia_10(this);
    // recipeDia10 = new RecipeDia10(this);
    // recipeDia10.registerGoldenApplePlusRecipe();
    // recipeDia10.registerGoldenApplePlusMaxRecipe();
    // recipeDia10.registerShieldOpRecipe();
    // getServer().getScheduler().runTaskTimer(this, new Runnable() {
    // @Override
    // public void run() {
    // for (Player player : getServer().getOnlinePlayers()) {
    // dia_10.onRainingAcid(player);
    // }
    // }

    // }, 0, 0);

    // Dia 8
    // dia_8.addEntities();
    // dia_8.addMobsPasives();

    // Dia 6
    // getServer().getScheduler().runTaskTimer(this, new Runnable() {
    // @Override
    // public void run() {
    // for (Player player : getServer().getOnlinePlayers()) {
    // Inventory inventory = player.getInventory();
    // if (inventory == null) {
    // return;
    // }
    // if (player.getInventory().getHelmet() != null &&
    // player.getInventory().getHelmet().isSimilar(getCustomHelmetItem())) {
    // removeNegativeEffect(player);
    // } else {
    // applyNegativeEffect(player);
    // }
    // if (player.getInventory().contains(getCustomBarrierItem())) {
    // dia_6.desblockInventory(player);
    // } else {
    // dia_6.blockedInventory(player);
    // }
    // }
    // }
    // }, 0L, 0L);
    // recipeDia6 = new RecipeDia6(this);
    // recipeDia6.registerCustomRecipe();
    // recipeDia6.registerCustomBarrierCrafting();
  }

  public void onDisable() {
    LOGGER.info("PLUGIN DEMO DISABLED");
  }

}