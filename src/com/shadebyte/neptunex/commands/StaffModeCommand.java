package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.STAFF_MODE_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            if (Core.getInstance().getStaffModePlayers().get(p) == null) {
                Core.getInstance().getStaffModePlayers().put(p, p.getInventory());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.STAFFMODE_ENTER.getNode()));
                p.getInventory().clear();

                //Set
                p.getInventory().setItem(1, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.LAUNCH_COMPASS));
                p.getInventory().setItem(2, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.TELEPORT));
                p.getInventory().setItem(3, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.EXAMINE));
                p.getInventory().setItem(4, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.CPS));
                p.getInventory().setItem(5, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_OFF));
                p.getInventory().setItem(6, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.RIDE));
                p.getInventory().setItem(7, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.FREEZE_ROD));
                return true;
            }

            p.getInventory().clear();
            p.getInventory().setContents(Core.getInstance().getStaffModePlayers().get(p).getContents());
            Core.getInstance().getStaffModePlayers().remove(p);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.STAFFMODE_EXIT.getNode()));
            return true;
        }
        return true;
    }

}
