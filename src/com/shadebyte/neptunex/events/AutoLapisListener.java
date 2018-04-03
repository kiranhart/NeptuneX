package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class AutoLapisListener implements Listener {

    @EventHandler
    public void onEnchantmentInventoryOpen(InventoryOpenEvent e) {

        if (!(e.getInventory() instanceof EnchantingInventory)) {
            return;
        }

        EnchantingInventory ei = (EnchantingInventory) e.getInventory();

        if (Core.getInstance().getConfig().getBoolean("auto-lapis-to-enchantment-tables")) {
            ei.setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
        }
    }

    @EventHandler
    public void onEnchantmentInventoryClick(InventoryClickEvent e) {

        if (!(e.getInventory() instanceof EnchantingInventory)) {
            return;
        }

        if (Core.getInstance().getConfig().getBoolean("auto-lapis-to-enchantment-tables") && e.getSlot() == 1) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchantmentInventoryClose(InventoryCloseEvent e) {

        if (!(e.getInventory() instanceof EnchantingInventory)) {
            return;
        }

        EnchantingInventory ei = (EnchantingInventory) e.getInventory();

        if (Core.getInstance().getConfig().getBoolean("auto-lapis-to-enchantment-tables")) {
            ei.setItem(1, new ItemStack(Material.AIR));
        }
    }
}
