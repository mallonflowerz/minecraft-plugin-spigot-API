package com.mallonflowerz.spigot.statics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Message {

    public void sendMessage(String message) {
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
