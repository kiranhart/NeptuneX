package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpBoostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(Permissions.JUMP_AFFECT_COMMAND.getNode())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            if (p.hasPotionEffect(PotionEffectType.JUMP)) {
                p.removePotionEffect(PotionEffectType.JUMP);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, Core.getInstance().getConfig().getInt("potion-commands.jumpboost") - 1, false, false), false);
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.JUMP_POTION_TOGGLE.getNode()));
        }
        return true;
    }
}
