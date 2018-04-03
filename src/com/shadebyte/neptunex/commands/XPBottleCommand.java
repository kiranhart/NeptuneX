package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XPBottleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0) {
            if (NeptuneAPI.getInstance().isPlayer(sender)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/xpbottle <amount>"));
            }
            return true;
        }

        if (args.length == 1) {
            if (!NeptuneAPI.getInstance().isPlayer(sender)) {
                return true;
            }

            if (!NeptuneAPI.getInstance().isInteger(args[0])) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                return true;
            }

            Player p = (Player) sender;

            if (!p.hasPermission(Permissions.XPBOTTLE_COMMAND.getNode())) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                return true;
            }

            int value = Integer.parseInt(args[0]);

            if (NeptuneAPI.getInstance().getXp(p) < value) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_ENOUGH_EXP.getNode()));
                return true;
            }

            if (value < Core.getInstance().getConfig().getInt("withdraw-numbers.min-exp-withdraw")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.MIN_EXP_WITHDRAW.getNode()));
                return true;
            }

            if (value > Core.getInstance().getConfig().getInt("withdraw-numbers.max-exp-withdraw")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.MAX_EXP_WITHDRAW.getNode()));
                return true;
            }

            if (NeptuneAPI.getInstance().hasAvaliableSlot(p)) {
                p.getInventory().addItem(NeptuneAPI.getInstance().createPlayerXPBottle(p, value));
                int exp = NeptuneAPI.getInstance().getXp(p);
                exp -= value;
                p.setTotalExperience(0);
                p.setLevel(0);
                p.setExp(0.0F);
                p.giveExp(exp);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.WITHDRAW_EXP.getNode().replace("{value}", NeptuneAPI.getInstance().formatNumber(value))));

            }
            return true;
        }

        if (args.length == 2) {

            if (!sender.hasPermission(Permissions.XPBOTTLE_COMMAND.getNode())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                return true;
            }

            if (!NeptuneAPI.getInstance().isInteger(args[0])) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                if (NeptuneAPI.getInstance().hasAvaliableSlot(target)) {
                    target.getInventory().addItem(NeptuneAPI.getInstance().createAdminXPBottle(Integer.parseInt(args[0])));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTarget's inventory has available slots."));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
            }

            return true;
        }

        return true;
    }
}
