package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.playervault.PVVaultGUI;
import com.shadebyte.neptunex.inventory.playervault.PVVaultSelectionGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerVaultCommand implements CommandExecutor {

    private PVVaultSelectionGUI pvVaultSelectionGUI;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.PLAYERVAULT_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            pvVaultSelectionGUI = new PVVaultSelectionGUI(p);
            p.openInventory(pvVaultSelectionGUI.getInventory());
            return true;
        }

        if (args.length == 1) {
            if (NeptuneAPI.getInstance().isInteger(args[0])) {
                int number = Integer.parseInt(args[0]);
                if (NeptuneAPI.getInstance().canUseVault(p, number)) {
                    p.openInventory(new PVVaultGUI(p, number).getInventory());
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
            }
            return true;
        }
        return true;
    }
}
