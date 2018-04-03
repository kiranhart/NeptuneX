package com.shadebyte.neptunex.commands;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.paginatedgui.buttons.GUIButton;
import com.shadebyte.neptunex.inventory.paginatedgui.types.PaginatedGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoucherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission(Permissions.VOUCHER_COMMAND.getNode())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher create <name>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher remove <name>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher give <player> <voucher> <#>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher giveall <voucher> <#>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher list"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "create":
                    if (sender.hasPermission(Permissions.VOUCHER_CREATE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher create &c<name>"));
                    }
                    break;
                case "remove":
                    if (sender.hasPermission(Permissions.VOUCHER_REMOVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher remove &c<name>"));
                    }
                    break;
                case "give":
                    if (sender.hasPermission(Permissions.VOUCHER_GIVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher give &c<player> <voucher> <#>"));
                    }
                    break;
                case "giveall":
                    if (sender.hasPermission(Permissions.VOUCHER_GIVEALL_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher giveall &c<voucher> <#>"));
                    }
                    break;
                case "list":
                    if (sender.hasPermission(Permissions.VOUCHER_LISTCOMMAND.getNode())) {
                        if (Core.getVoucherFile().getConfig().getConfigurationSection("vouchers").getKeys(false).size() == 0 || Core.getVoucherFile().getConfig().getConfigurationSection("vouchers") == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_NONE.getNode()));
                            return true;
                        }

                        if (sender instanceof Player) {

                            PaginatedGUI menu = new PaginatedGUI("&B&LVouchers");
                            GUIButton button;
                            int index = 0;
                            for (String vouchers : Core.getVoucherFile().getConfig().getConfigurationSection("vouchers").getKeys(false)) {
                                button = new GUIButton(NeptuneAPI.getInstance().createVoucher(vouchers));
                                menu.setButton(index, button);
                                index++;
                            }
                            Player p = (Player) sender;
                            p.openInventory(menu.getInventory());
                        } else {
                            for (String vouchers : Core.getVoucherFile().getConfig().getConfigurationSection("vouchers").getKeys(false)) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l" + vouchers));
                            }
                        }
                    }
                    break;
            }
            return true;
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "create":
                    if (!sender.hasPermission(Permissions.VOUCHER_CREATE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    if (Core.getVoucherFile().getConfig().contains("vouchers." + args[1].toLowerCase())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_EXIST.getNode().replace("{name}", args[1])));
                        return true;
                    }

                    NeptuneAPI.getInstance().registerNewVoucher(args[1].toLowerCase());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_CREATE.getNode().replace("{name}", args[1])));
                    break;
                case "remove":
                    if (!sender.hasPermission(Permissions.VOUCHER_REMOVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    if (!Core.getVoucherFile().getConfig().contains("vouchers." + args[1].toLowerCase())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_INVALID.getNode().replace("{name}", args[1])));
                        return true;
                    }

                    NeptuneAPI.getInstance().unregisterNewVoucher(args[1].toLowerCase());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_REMOVE.getNode().replace("{name}", args[1])));
                    break;
                case "give":
                    if (!sender.hasPermission(Permissions.VOUCHER_GIVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher give &c<player> <voucher> <#>".replace("<player>", args[1])));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                    }
                    break;
                case "giveall":
                    if (!sender.hasPermission(Permissions.VOUCHER_GIVEALL_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    if (!Core.getVoucherFile().getConfig().contains("vouchers." + args[1].toLowerCase())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_INVALID.getNode().replace("[name}", args[1])));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher giveall &c<voucher> <#>".replace("<voucher>", args[1])));
                    }
                    break;
            }
            return true;
        }

        if (args.length == 3) {
            switch (args[0].toLowerCase()) {
                case "give":
                    if (!sender.hasPermission(Permissions.VOUCHER_GIVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/Voucher give &c<player> <voucher> <#>".replace("<player>", args[1])));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                    }
                    break;
                case "giveall":
                    if (!sender.hasPermission(Permissions.VOUCHER_GIVEALL_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    if (!Core.getVoucherFile().getConfig().contains("vouchers." + args[1].toLowerCase())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_INVALID.getNode().replace("[name}", args[1])));
                    } else {
                        if (NeptuneAPI.getInstance().isInteger(args[2])) {
                            for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                                Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().addItem(NeptuneAPI.getInstance().createVoucher(args[1])));
                            }
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_GIVEALL.getNode().replace("{amount}", args[2]).replace("[name}", args[1])));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                        }
                    }
                    break;
            }
            return true;
        }

        if (args.length == 4) {
            switch (args[0].toLowerCase()) {
                case "give":
                    if (!sender.hasPermission(Permissions.VOUCHER_GIVE_COMMAND.getNode())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION.getNode()));
                        return true;
                    }

                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        if (!Core.getVoucherFile().getConfig().contains("vouchers." + args[2].toLowerCase())) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_INVALID.getNode().replace("[name}", args[2])));
                        } else {
                            if (NeptuneAPI.getInstance().isInteger(args[3])) {
                                for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                                    target.getInventory().addItem(NeptuneAPI.getInstance().createVoucher(args[2]));
                                }
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VOUCHER_GIVE.getNode().replace("{amount}", args[3]).replace("{player}", args[1]).replace("[name}", args[2])));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOT_A_NUMBER.getNode()));
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.PLAYER_OFFLINE.getNode()));
                    }
                    break;
            }
            return true;
        }
        return true;
    }
}
