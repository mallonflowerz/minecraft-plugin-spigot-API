package com.mallonflowerz.spigot.items;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefinitiveArmor {

    public static ItemStack craftDefinitiveHelmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);

        EquipmentSlot slot = EquipmentSlot.HEAD;

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_BLUE + "Definitive Helmet");
        meta.setUnbreakable(true);

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier2);

        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", 3.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modifier3);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack craftDefinitiveChest() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_BLUE + "Definitive Chestplate");
        meta.setUnbreakable(true);

        EquipmentSlot slot = EquipmentSlot.CHEST;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 9.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier2);

        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", 3.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modifier3);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack craftDefinitiveLeggings() {
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_BLUE + "Definitive Leggings");
        meta.setUnbreakable(true);

        EquipmentSlot slot = EquipmentSlot.LEGS;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 7.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier2);

        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", 3.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modifier3);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack craftDefinitiveBoots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_BLUE + "Definitive Boots");
        meta.setUnbreakable(true);

        EquipmentSlot slot = EquipmentSlot.FEET;

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 4.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier2);

        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", 3.0D,
                AttributeModifier.Operation.ADD_NUMBER, slot);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modifier3);

        item.setItemMeta(meta);

        return item;
    }

    public static boolean isDefinitivePiece(ItemStack s) {
        if (s == null)
            return false;

        if (s.hasItemMeta()) {
            if (s.getItemMeta().isUnbreakable() &&
                    ChatColor.stripColor(s.getItemMeta().getDisplayName()).startsWith("Definitive")) {
                return true;
            }
        }
        return false;
    }
}
