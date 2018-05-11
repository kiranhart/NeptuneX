package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemTagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYERS_ONLY.getNode()));
            return true;
        }

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give <player> <#>"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag giveall <#>"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag self <#>"));
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("self")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag self &c<#>"));
                } else if (args[0].equalsIgnoreCase("give")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give &c<player> <#>"));
                } else if (args[0].equalsIgnoreCase("giveall")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag giveall &c<#>"));
                }
                return true;
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("self")) {
                    if (!NeptuneAPI.getInstance().isInteger(args[1])) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                        p.getInventory().addItem(NeptuneAPI.getInstance().nametag());
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_SELF.getNode().replace("{amount}", String.valueOf(Integer.parseInt(args[1])))));
                } else if (args[0].equalsIgnoreCase("giveall")) {
                    if (!NeptuneAPI.getInstance().isInteger(args[1])) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                        Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().addItem(NeptuneAPI.getInstance().nametag()));
                    }
                    Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_ALL.getNode().replace("{player}", p.getName()).replace("{amount}", String.valueOf(Integer.parseInt(args[1]))))));
                } else if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give " + target.getName() + " &c<#>"));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                    }
                }
                return true;
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (!NeptuneAPI.getInstance().isInteger(args[2])) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                            return true;
                        }
                        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                            target.getInventory().addItem(NeptuneAPI.getInstance().nametag());
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_SENDER.getNode().replace("{target}", args[1]).replace("{amount}", String.valueOf(Integer.parseInt(args[2])))));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_PLAYER.getNode().replace("{player}", p.getName()).replace("{amount}", String.valueOf(Integer.parseInt(args[2])))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                    }
                }
                return true;
            }

            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give <player> <#>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag giveall <#>"));
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("give")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give &c<player> <#>"));
            } else if (args[0].equalsIgnoreCase("giveall")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag giveall &c<#>"));
            }
            return true;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("giveall")) {
                if (!NeptuneAPI.getInstance().isInteger(args[1])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                    return true;
                }
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().addItem(NeptuneAPI.getInstance().nametag()));
                }
                Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_ALL.getNode().replace("{player}", sender.getName()).replace("{amount}", String.valueOf(Integer.parseInt(args[1]))))));
            } else if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/nametag give " + target.getName() + " &c<#>"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                }
            }
            return true;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (!NeptuneAPI.getInstance().isInteger(args[2])) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                        return true;
                    }
                    for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                        target.getInventory().addItem(NeptuneAPI.getInstance().nametag());
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_SENDER.getNode().replace("{target}", args[1]).replace("{amount}", String.valueOf(Integer.parseInt(args[2])))));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_PLAYER.getNode().replace("{player}", sender.getName()).replace("{amount}", String.valueOf(Integer.parseInt(args[2])))));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                }
            }
            return true;
        }

        return true;
    }
}