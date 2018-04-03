package com.shadebyte.neptunex.inventory;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class OresGUI implements NeptuneGUI {

    private Player p;

    public OresGUI(Player p) {
        this.p = p;
    }

    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(this, Core.getInstance().getConfig().getInt("guis.ores.size"), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("guis.ores.title")));
        for (String node : Core.getInstance().getConfig().getConfigurationSection("guis.ores.slots").getKeys(false)) {
            gui.setItem(Integer.valueOf(Integer.parseInt(node) - 1), NeptuneAPI.getInstance().createOreGUIItem(p, Material.valueOf(Core.getInstance().getConfig().getString("guis.ores.slots." + node + ".type").toUpperCase()), Integer.parseInt(node)));
        }
        return gui;
    }
}
