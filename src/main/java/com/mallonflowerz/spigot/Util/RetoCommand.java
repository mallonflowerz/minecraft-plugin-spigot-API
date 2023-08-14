package com.mallonflowerz.spigot.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mallonflowerz.spigot.Plugin;
import com.mallonflowerz.spigot.items.DefinitiveArmor;
import com.mallonflowerz.spigot.items.UpgradeNetherite;

public class RetoCommand implements CommandExecutor {

    private Plugin plugin;

    public RetoCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("probtotem")) {
            if (args[1] != null || Numeric.isNumeric(args[1])) {
                int prob = Integer.parseInt(args[1]);
                plugin.setProb(prob);
                sender.sendMessage(String.format("La probabilidad de activar los totems es: %s/100", prob));
                plugin.reloadConfig();
                return true;
            } else {
                sender.sendMessage("Se espera un valor numerico");
                return false;
            }
        } else if (args[0].equalsIgnoreCase("setday")) {
            if (Numeric.isNumeric(args[1])) {
                int days = Integer.parseInt(args[1]);
                plugin.setDays(days);
                sender.sendMessage("Días cambiados a: " + days);
                plugin.reloadConfig();
                return true;
            } else {
                sender.sendMessage("El argumento esperado es numerico");
                return false;
            }
        } else if (args[0].equalsIgnoreCase("getday")) {
            Integer day = plugin.getDays();
            sender.sendMessage("El dia del plugin es: " + day);
            return true;
        } else if (args[0].equalsIgnoreCase("armorDefinitive")) {
            Player player = plugin.getServer().getPlayer(args[1].toString());
            player.getInventory().addItem(DefinitiveArmor.craftDefinitiveBoots(),
                    DefinitiveArmor.craftDefinitiveChest(),
                    DefinitiveArmor.craftDefinitiveHelmet(),
                    DefinitiveArmor.craftDefinitiveLeggings());
            sender.sendMessage("Armadura Definitiva dada a: " + player.getName());
            return true;
        } else if (args[0].equalsIgnoreCase("herramientasop")) {
            Player player = plugin.getServer().getPlayer(args[1].toString());
            player.getInventory().addItem(UpgradeNetherite.craftAxe(),
                    UpgradeNetherite.craftShovel(),
                    UpgradeNetherite.craftSword(),
                    UpgradeNetherite.craftHoe(),
                    UpgradeNetherite.craftPickaxe());
            sender.sendMessage("Herramientas dada a: " + player.getName());
            return true;
        }
        return false;
    }
}
