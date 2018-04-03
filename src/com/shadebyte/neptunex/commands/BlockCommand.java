package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.BLOCK_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {

            // Coal
            transform(p, 263, Material.COAL, Material.COAL_BLOCK, 0);
            //Iron
            transform(p, 265, Material.IRON_INGOT, Material.IRON_BLOCK, 0);
            //Redstone
            transform(p, 331, Material.REDSTONE, Material.REDSTONE_BLOCK, 0);
            //Gold
            transform(p, 266, Material.GOLD_INGOT, Material.GOLD_BLOCK, 0);
            //Diamond
            transform(p, 264, Material.DIAMOND, Material.DIAMOND_BLOCK, 0);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.INGOT_CONVERT.getNode()));
            return true;
        }
        return true;
    }

    private void transform(Player p, int id, Material item, Material block, int data) {
        int amount = NeptuneAPI.getInstance().getAmount(p, id);
        int remainder = amount % 9;
        int total = amount / 9;
        if (amount >= 1) {
            p.getInventory().remove(item);
            p.getInventory().addItem(new ItemStack(block, total));
            if (remainder != 0) {
                p.getInventory().addItem(new ItemStack(item, remainder));
            }
            p.updateInventory();
        }
    }
}
