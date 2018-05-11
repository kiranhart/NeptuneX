package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveallCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.GIVEALL_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/giveall <#>"));
            return true;
        }

        if (args.length == 1) {
            if (!NeptuneAPI.getInstance().isInteger(args[0])) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                return true;
            }

            ItemStack hand = NeptuneAPI.getItemInHand(p).clone();
            hand.setAmount(1);

            if (hand == null || hand.getType() == Material.AIR) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOTHING_IN_HAND.getNode()));
                return true;
            }

            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().addItem(hand));
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_GIVEALL.getNode()));
            return true;
        }

        return true;
    }
}