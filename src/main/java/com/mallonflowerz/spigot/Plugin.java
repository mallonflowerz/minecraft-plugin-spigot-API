package com.mallonflowerz.spigot;

import java.util.logging.Logger;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.mallonflowerz.spigot.AbyssalDimension.AbyssalManager;
import com.mallonflowerz.spigot.End.EndConfig;
import com.mallonflowerz.spigot.Listener.Block.BlockEvents;
import com.mallonflowerz.spigot.Listener.Entity.EntityEvents;
import com.mallonflowerz.spigot.Listener.Entity.SpawnListener;
import com.mallonflowerz.spigot.Listener.Entity.TotemConsumeEvent;
import com.mallonflowerz.spigot.Listener.Player.PlayerEvents;
import com.mallonflowerz.spigot.Listener.World.WorldEvents;
import com.mallonflowerz.spigot.Util.RetoCommand;
import com.mallonflowerz.spigot.data.AbyssalDataManager;
import com.mallonflowerz.spigot.items.DiamondApple;
import com.mallonflowerz.spigot.items.UpgradeNetherite;

import lombok.Getter;
import lombok.Setter;

public class Plugin extends JavaPlugin {

  private static final Logger LOGGER = Logger.getLogger("plugin-demo");

  public static boolean worldEditFound;

  @Getter
  public static Plugin instance;

  public World world = null;

  @Getter
  private AbyssalDataManager beData;

  @Getter
  private AbyssalManager abyssalManager;

  private RetoCommand command = new RetoCommand(this);
  private UpgradeNetherite netherite = new UpgradeNetherite(this);
  private DiamondApple apple = new DiamondApple(this);

  @Getter
  @Setter
  private Integer days = 0;

  @Getter
  public static boolean runningPaperSpigot = false;

  @Getter
  @Setter
  private Integer prob = 0;

  public void onEnable() {
    instance = this;
    if (instance.getConfig() != null) {
      System.out.println(instance.getConfig().toString());
      // world = Bukkit.getWorld(Objects.requireNonNull(instance.getConfig()));
    }
    startPlugin();
    days = getConfig().getInt("days", 0);
    prob = getConfig().getInt("prob", 0);
    LOGGER.info("PLUGIN ENABLED");
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
      getServer().addRecipe(apple.registerRecipeDiamondApple());
      this.beData = new AbyssalDataManager(this);
      this.abyssalManager = new AbyssalManager(this);
    }
  }

  @Override
  public void onLoad() {
    instance = this;
  }

  public void onDisable() {
    instance = null;
    LOGGER.info("PLUGIN DISABLED");
    getConfig().set("days", days);
    getConfig().set("prob", prob);
    saveConfig();
  }

  private void startPlugin() {
    worldEditFound = (Bukkit.getPluginManager().getPlugin("WorldEdit") != null
        || Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null);
  }

}