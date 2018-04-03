package com.shadebyte.neptunex.inventory;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class FreezeGUI implements NeptuneGUI {

    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (Core.getInstance().getFrozenPlayers().contains(p)) {
            Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
                p.openInventory(getInventory());
            }, 1);
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(this, Core.getInstance().getConfig().getInt("guis.freeze.size"), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.freeze.title")));
        for (int i = 0; i < Core.getInstance().getConfig().getInt("guis.freeze.size"); i++) {
            gui.setItem(i, NeptuneAPI.getInstance().createConfigItem("guis.freeze.fill.filler"));
        }

        for (String nodes : Core.getInstance().getConfig().getConfigurationSection("guis.freeze.slots").getKeys(false)) {
            gui.setItem(Integer.valueOf(Integer.parseInt(nodes) - 1), NeptuneAPI.getInstance().createConfigItem("guis.freeze.slots." + nodes));
        }
        return gui;
    }
}
