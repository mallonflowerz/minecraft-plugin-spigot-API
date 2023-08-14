package com.mallonflowerz.spigot;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.mallonflowerz.spigot.End.EndConfig;
import com.mallonflowerz.spigot.Listener.Block.BlockEvents;
import com.mallonflowerz.spigot.Listener.Entity.EntityEvents;
import com.mallonflowerz.spigot.Listener.Entity.SpawnListener;
import com.mallonflowerz.spigot.Listener.Entity.TotemConsumeEvent;
import com.mallonflowerz.spigot.Listener.Player.PlayerEvents;
import com.mallonflowerz.spigot.Listener.World.WorldEvents;
import com.mallonflowerz.spigot.Util.RetoCommand;
import com.mallonflowerz.spigot.items.UpgradeNetherite;

public class Plugin extends JavaPlugin {

  private static final Logger LOGGER = Logger.getLogger("plugin-demo");

  private RetoCommand command = new RetoCommand(this);
  private UpgradeNetherite netherite = new UpgradeNetherite(this);

  private Integer days = 0;
  private Integer prob = 0;

  public void onEnable() {
    days = getConfig().getInt("days", 0);
    prob = getConfig().getInt("prob", 0);
    LOGGER.info("PLUGIN DEMO ENABLED");
    getCommand("reto").setExecutor(command);
    getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
    getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
    getServer().getPluginManager().registerEvents(new EntityEvents(this), this);
    getServer().getPluginManager().registerEvents(new TotemConsumeEvent(this), this);
    getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    getServer().getPluginManager().registerEvents(new WorldEvents(this), this);

    if (days >= 10) {
      getServer().getPluginManager().registerEvents(new EndConfig(this), this);
    }

    if (days >= 14) {
      getServer().addRecipe(netherite.registerHoe());
      getServer().addRecipe(netherite.registerShovel());
      getServer().addRecipe(netherite.registerSword());
      getServer().addRecipe(netherite.registerPickaxe());
      getServer().addRecipe(netherite.registerAxe());
    }
  }

  public void onDisable() {
    LOGGER.info("PLUGIN DEMO DISABLED");
    getConfig().set("days", days);
    getConfig().set("prob", prob);
    saveConfig();
  }

  public Integer getProb() {
    return prob;
  }

  public void setProb(Integer prob) {
    this.prob = prob;
  }

  public Integer getDays() {
    return days;
  }

  public void setDays(Integer days) {
    this.days = days;
  }

}