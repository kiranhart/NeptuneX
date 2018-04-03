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

public class CPSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.CPS_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/CPS <player>"));
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {
                if (!Core.getInstance().getClicks().containsKey(target)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCPS Check began, please wait 10 seconds for data to be collected."));
                    Core.getInstance().getClicks().put(target, 0);
                    Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.CPSCHECK_RESULT.getNode().replace("{cps}", String.valueOf(Core.getInstance().getClicks().get(target) / 10)).replace("{player}", args[0])));
                        Core.getInstance().getClicks().remove(target);
                    }, 20 * 10);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.CPSCHECK_ACTIVE.getNode()));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
            }
            return true;
        }

        return true;
    }
}
