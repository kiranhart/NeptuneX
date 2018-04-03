package com.shadebyte.neptunex.commands;

import java.util.UUID;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.FreezeGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

	private FreezeGUI freezeGUI;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

		if (!sender.hasPermission(Permissions.FREEZE_COMMAND.getNode())) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l(!) &b/freeze <player>"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l(!) &b/freeze listall"));
			return true;
		}

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("listall")) {
				if (Core.getInstance().getFrozenPlayers().size() <= 0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!) &cNo frozen players were found!"));
				} else {
					String msg = "";

					for (Player ids : Core.getInstance().getFrozenPlayers()) {
						msg += ", " + ids.getName();
					}

					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l(!) &bFrozen Players:"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
				}
				return true;
			}

			Player target = Bukkit.getPlayerExact(args[0]);

			if (target == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
				return true;
			}

			if (!Core.getInstance().getFrozenPlayers().contains(target)) {
				Core.getInstance().getFrozenPlayers().add(target);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_FREEZER_FROZE.getNode().replace("{player}", args[0])));
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_TARGET_FROZE.getNode().replace("{player}", sender.getName())));

				freezeGUI = new FreezeGUI();
				target.openInventory(freezeGUI.getInventory());
			} else {
				Core.getInstance().getFrozenPlayers().remove(target);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_FREEZER_UNFROZE.getNode().replace("{player}", args[0])));
				target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_TARGET_UNFROZE.getNode().replace("{player}", sender.getName())));
				target.closeInventory();
			}
			return true;
		}
		return true;
	}
}
