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

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.FEED_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {

            if (Core.getPlayerFile().getConfig().contains("players." + p.getUniqueId().toString() + ".feed-cooldown")) {
                long configTime = Core.getPlayerFile().getConfig().getLong("players." + p.getUniqueId().toString() + ".feed-cooldown");
                if (configTime < System.currentTimeMillis()) {
                    p.setFoodLevel(20);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FEED_FED.getNode()));
                    Core.getPlayerFile().getConfig().set("players." + p.getUniqueId().toString() + ".feed-cooldown", System.currentTimeMillis() + (Core.getInstance().getConfig().getInt("delays.feed") * 1000));
                    Core.getPlayerFile().saveConfig();
                } else {
                    long timeLeft = (configTime - System.currentTimeMillis()) / 1000;
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FEED_COOLDOWN.getNode().replace("{timeleft}", String.valueOf(timeLeft))));
                }
            } else {
                p.setFoodLevel(20);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FEED_FED.getNode()));
                Core.getPlayerFile().getConfig().set("players." + p.getUniqueId().toString() + ".feed-cooldown", System.currentTimeMillis() + (Core.getInstance().getConfig().getInt("delays.feed") * 1000));
                Core.getPlayerFile().saveConfig();
            }
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (p.hasPermission(Permissions.STAFF_PERM.getNode())) {
                if (target != null) {
                    target.setFoodLevel(20);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FEED_CANNOT_FEED_OTHERS.getNode()));
            }
            return true;
        }

        return true;
    }
}
