package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.HEAL_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {

            if (Core.getPlayerFile().getConfig().contains("players." + p.getUniqueId().toString() + ".heal-cooldown")) {
                long configTime = Core.getPlayerFile().getConfig().getLong("players." + p.getUniqueId().toString() + ".heal-cooldown");
                if (configTime < System.currentTimeMillis()) {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.HEAL_HEALED.getNode()));
                    Core.getPlayerFile().getConfig().set("players." + p.getUniqueId().toString() + ".heal-cooldown", System.currentTimeMillis() + (Core.getInstance().getConfig().getInt("delays.heal") * 1000));
                    Core.getPlayerFile().saveConfig();
                } else {
                    long timeLeft = (configTime - System.currentTimeMillis()) / 1000;
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.HEAL_COOLDOWN.getNode().replace("{timeleft}", String.valueOf(timeLeft))));
                }
            } else {
                p.setHealth(20);
                p.setFoodLevel(20);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.HEAL_HEALED.getNode()));
                Core.getPlayerFile().getConfig().set("players." + p.getUniqueId().toString() + ".heal-cooldown", System.currentTimeMillis() + (Core.getInstance().getConfig().getInt("delays.heal") * 1000));
                Core.getPlayerFile().saveConfig();
            }
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (p.hasPermission(Permissions.STAFF_PERM.getNode())) {
                if (target != null) {
                    target.setHealth(20);
                    target.setFoodLevel(20);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.HEAL_CANNOT_HEAL_OTHERS.getNode()));
            }
            return true;
        }

        return true;
    }
}
