package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.BotCheckInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CaptchaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!sender.hasPermission(Permissions.CAPTCHA_COMMAND.getNode())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Captcha <player>"));
            return true;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null) {

                if (Core.getInstance().getAuraBotCheck().contains(target)) {
                    Core.getInstance().getAuraBotCheck().remove(target);
                    target.closeInventory();
                }

                Core.getInstance().getAuraBotCheck().add(target);
                target.openInventory(new BotCheckInventory().getInventory());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.BOT_CHECK_SENT.getNode().replace("{player}", args[0])));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
            }
            return true;
        }

        return true;
    }
}
