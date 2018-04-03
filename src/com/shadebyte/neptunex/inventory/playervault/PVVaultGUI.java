package com.shadebyte.neptunex.inventory.playervault;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.inventory.NeptuneGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class PVVaultGUI implements NeptuneGUI {

    private Player p;
    private int vault;

    public PVVaultGUI(Player p, int vault) {
        this.p = p;
        this.vault = vault;
    }


    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {

    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {

        String name = Core.getInstance().getConfig().getString("player-vaults.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(vault));
        String icon = Core.getInstance().getConfig().getString("player-vaults.vault-selection.default-item");

        if (!Core.getVaultFile().getConfig().contains("players." + e.getPlayer().getUniqueId().toString() + "." + vault)) {
            Core.getVaultFile().getConfig().set("players." + e.getPlayer().getUniqueId().toString() + "." + vault + ".icon", icon);
            Core.getVaultFile().getConfig().set("players." + e.getPlayer().getUniqueId().toString() + "." + vault + ".name", ChatColor.translateAlternateColorCodes('&', name));
        }

        for (int i = 0; i < e.getInventory().getSize(); i++) {
            Core.getVaultFile().getConfig().set("players." + e.getPlayer().getUniqueId().toString() + "." + vault + ".contents." + i, e.getInventory().getItem(i));
        }

        Core.getVaultFile().saveConfig();
        //TODO OPEN SELECTION
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, NeptuneAPI.getInstance().getMaxSize(p), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("player-vaults.vault-title") + " #" + vault));
        if (Core.getVaultFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault)) {
            if (!Core.getVaultFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault + ".contents")) {
                return inv;
            }

            for (String keys : Core.getVaultFile().getConfig().getConfigurationSection("players." + p.getUniqueId().toString() + "." + vault + ".contents").getKeys(false)) {
                int slot = Integer.parseInt(keys);
                inv.setItem(slot, Core.getVaultFile().getConfig().getItemStack("players." + p.getUniqueId().toString() + "." + vault + ".contents." + keys));
            }
        } else {
            return inv;
        }
        return inv;
    }
}
