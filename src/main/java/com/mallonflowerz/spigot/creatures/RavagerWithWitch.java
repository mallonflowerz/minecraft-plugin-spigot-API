package com.mallonflowerz.spigot.creatures;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RavagerWithWitch {

    private Random random = new Random();

    private ItemStack customPotion() {
        ItemStack potionItem = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potionItem.getItemMeta();

        meta.addCustomEffect(
                new PotionEffect(PotionEffectType.REGENERATION, 2 * 1200, 1), true);
        potionItem.setItemMeta(meta);
        return potionItem;
    }

    public Ravager spawnRavager(Location location) {
        Ravager ravager = location.getWorld().spawn(location, Ravager.class);
        Witch witch = location.getWorld().spawn(location, Witch.class);
        ravager.setCustomName("Ravager With Witch");
        witch.setCustomName("Bruja Instant");
        ravager.getEquipment().setLeggings(new ItemStack(Material.TOTEM_OF_UNDYING));
        ravager.getEquipment().setLeggingsDropChance(5.0F);
        witch.getEquipment().setBoots(customPotion());
        witch.getEquipment().setBootsDropChance(5.0F);
        int x = random.nextInt(4) + 1;
        double originalMaxHealth = ravager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double newMaxHealth = originalMaxHealth * x;
        ravager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
        int x2 = random.nextInt(3) + 1;
        double originalMaxHealth2 = witch.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double newMaxHealth2 = originalMaxHealth2 * x2;
        witch.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth2);
        ravager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);
        ravager.addPassenger(witch);
        return ravager;
    }

}
