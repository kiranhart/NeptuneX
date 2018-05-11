package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CreeperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.CREEPER_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {

            Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
            if (Core.getInstance().getConfig().getBoolean("creepercmd.use-nametag")) {
                creeper.setCustomName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("creepercmd.nametag").replace("{player}", p.getName())));
            }

            if (!Core.getInstance().getConfig().getBoolean("creepercmd.other-players-can-damage")) {
                creeper.setMetadata("owner", new FixedMetadataValue(Core.getInstance(), p.getUniqueId()));
            }

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.CREEPER_SPAWN.getNode()));
            return true;
        }

        return true;
    }
}
