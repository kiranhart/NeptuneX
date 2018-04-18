package com.shadebyte.neptunex.inventory;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BotCheckInventory implements NeptuneGUI {

    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
            if (Core.getInstance().getAuraBotCheck().contains(p)) {
                Core.getInstance().getAuraBotCheck().remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.BOT_CHECK_COMPLETE.getNode()));
            }
        } else {
            if (Core.getInstance().getConfig().getBoolean("botcheck.custom-fail-commands.enabled")) {
                for (String s : Core.getInstance().getConfig().getStringList("botcheck.custom-fail-commands.commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", p.getName()));
                }
            }

            if (Core.getInstance().getConfig().getBoolean("botcheck.kick-player-on-fail")) {
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("botcheck.kick-message")));
            }

            if (Core.getInstance().getConfig().getBoolean("botcheck.alert-staff-on-fail")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission(Permissions.STAFF_PERM.getNode())) {
                        continue;
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.BOT_CHECK_FAIL.getNode().replace("{player}", p.getName())));
                }
            }
        }
        e.getWhoClicked().closeInventory();
        return;
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {
        if (Core.getInstance().getAuraBotCheck().contains(e.getPlayer())) {
            Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
                e.getPlayer().openInventory(getInventory());
            }, 1);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory gui;
        List<Material> items = new ArrayList<>();
        int size = Core.getInstance().getConfig().getInt("botcheck.inventory.size");

        for (String s : Core.getInstance().getConfig().getStringList("botcheck.material-list")) {
            items.add(Material.valueOf(s.toUpperCase()));
        }

        gui = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("botcheck.inventory.title-glow-item")));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(items.get(ThreadLocalRandom.current().nextInt(items.size())), 1));
        }

        ItemStack glowing = gui.getItem(ThreadLocalRandom.current().nextInt(size)).clone();
        glowing.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta meta = glowing.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        glowing.setItemMeta(meta);
        gui.setItem(ThreadLocalRandom.current().nextInt(size), glowing);

        return gui;
    }
}
