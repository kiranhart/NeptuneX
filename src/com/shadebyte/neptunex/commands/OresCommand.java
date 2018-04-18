package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.enums.Language;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.OresGUI;

public class OresCommand implements CommandExecutor {

    private OresGUI oresGUI;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.ORES_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            oresGUI = new OresGUI(p);
            p.openInventory(oresGUI.getInventory());
            return true;
        }
        return true;
    }
}
