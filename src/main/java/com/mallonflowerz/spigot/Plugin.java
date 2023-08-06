package com.mallonflowerz.spigot;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mallonflowerz.spigot.days.Dia_2;
import com.mallonflowerz.spigot.days.Dia_4;
import com.mallonflowerz.spigot.days.Dia_6;
import com.mallonflowerz.spigot.days.Dia_8;
import com.mallonflowerz.spigot.items.ChestTop;

public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("plugin-demo");

  private final ChestTop chestTop = new ChestTop();
  private final Dia_6 dia_6 = new Dia_6();
  private final Dia_8 dia_8 = new Dia_8();

  public void onEnable() {
    LOGGER.info("PLUGIN DEMO ENABLED");
    // chestTop.spawnChestWithItemsTop(new
    // Location(getServer().getWorld(Mundos.WORLD_OVERWORLD), 0, 90, 0));
    getServer().getPluginManager().registerEvents(dia_8, this);
    dia_8.addEntities();
    dia_8.addMobsPasives();
    // Dia 6
    // getServer().getPluginManager().registerEvents(dia_6, this);
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
    // registerCustomRecipe();
    // registerCustomBarrierCrafting();
  }

  public void onDisable() {
    LOGGER.info("PLUGIN DEMO DISABLED");
  }

  private void registerCustomRecipe() {
    ItemStack customItem = new ItemStack(Material.NETHERITE_HELMET);
    ItemMeta itemMeta = customItem.getItemMeta();

    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
    itemMeta.setDisplayName("\u00A7r\u00A76Space Helmet");
    itemMeta.setLore(null);

    customItem.setItemMeta(itemMeta);

    // Porro Antivicio
    ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "custom_helmet"), customItem);
    recipe.shape("ABA", "XEX", "DCD"); // Definir la forma de la receta

    // Definir los materiales correspondientes a cada letra de la forma
    recipe.setIngredient('A', Material.NETHERITE_INGOT);
    recipe.setIngredient('B', Material.GOLD_BLOCK);
    recipe.setIngredient('C', Material.WITHER_SKELETON_SKULL);
    recipe.setIngredient('D', Material.DIAMOND_BLOCK);
    recipe.setIngredient('E', Material.TOTEM_OF_UNDYING);

    getServer().addRecipe(recipe);
  }

  private void registerCustomBarrierCrafting() {
    ItemStack elCleanItem = new ItemStack(Material.BARRIER);
    ItemMeta elCleanMeta = elCleanItem.getItemMeta();

    elCleanMeta.addEnchant(Enchantment.DURABILITY, 1, false);
    elCleanMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

    elCleanMeta.setDisplayName("\u00A79El Clean");
    elCleanMeta.setLore(null);

    elCleanItem.setItemMeta(elCleanMeta);

    // El Clean
    ShapedRecipe elClean = new ShapedRecipe(new NamespacedKey(this, "custom_barrier"), elCleanItem);
    elClean.shape("AAA", "BCB", "AAA"); // Definir la forma de la receta

    // Definir los materiales correspondientes a cada letra de la forma
    elClean.setIngredient('A', Material.OBSIDIAN);
    elClean.setIngredient('B', Material.DIAMOND_BLOCK);
    elClean.setIngredient('C', Material.NETHERITE_INGOT);

    getServer().addRecipe(elClean);
  }

  private void applyNegativeEffect(Player player) {
    player.damage(5);
    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0));
  }

  private void removeNegativeEffect(Player player) {
    player.removePotionEffect(PotionEffectType.POISON);
  }

  private ItemStack getCustomHelmetItem() {
    ItemStack customItem = new ItemStack(Material.NETHERITE_HELMET);
    ItemMeta itemMeta = customItem.getItemMeta();

    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
    itemMeta.setDisplayName("\u00A7r\u00A76Space Helmet");
    itemMeta.setLore(null);

    customItem.setItemMeta(itemMeta);
    return customItem;
  }

  private ItemStack getCustomBarrierItem() {
    ItemStack elCleanItem = new ItemStack(Material.BARRIER);
    ItemMeta elCleanMeta = elCleanItem.getItemMeta();

    elCleanMeta.addEnchant(Enchantment.DURABILITY, 1, false);
    elCleanMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

    elCleanMeta.setDisplayName("\u00A79El Clean");
    elCleanMeta.setLore(null);

    elCleanItem.setItemMeta(elCleanMeta);

    return elCleanItem;
  }

}