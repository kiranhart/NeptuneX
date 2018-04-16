package com.shadebyte.neptunex.inventory;

import com.shadebyte.neptunex.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
            e.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {
        Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
            e.getPlayer().openInventory(getInventory());
        }, 1);
    }

    @Override
    public Inventory getInventory() {
        Inventory gui;
        List<Material> items = new ArrayList<>();
        int size = Core.getInstance().getConfig().getInt("botcheck.inventory.size");

        for (String s : Core.getInstance().getConfig().getStringList("botcheck.material-list")) {
            items.add(Material.valueOf(s.toUpperCase()));
        }

        if (Core.getInstance().getConfig().getBoolean("botcheck.use-glow-item")) {
            gui = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("botcheck.inventory.title-glow-item")));
        } else {
            gui = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("botcheck.inventory.title-select-item").replace("{item}", items.get(ThreadLocalRandom.current().nextInt(items.size())).name())));
        }

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemStack(items.get(ThreadLocalRandom.current().nextInt(items.size())), 1));
        }

        if (Core.getInstance().getConfig().getBoolean("botcheck.use-glow-item")) {
            ItemStack glowing = gui.getItem(ThreadLocalRandom.current().nextInt(size)).clone();
            glowing.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            ItemMeta meta = glowing.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            glowing.setItemMeta(meta);
            gui.setItem(ThreadLocalRandom.current().nextInt(size), glowing);
        }

        return gui;
    }
}
