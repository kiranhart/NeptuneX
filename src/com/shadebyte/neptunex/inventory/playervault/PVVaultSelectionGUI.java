package com.shadebyte.neptunex.inventory.playervault;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.inventory.NeptuneGUI;
import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PVVaultSelectionGUI implements NeptuneGUI {

    private Player p;
    private PVIconSelectionGUI pvIconSelectionGUI;
    private PVVaultGUI pvVaultGUI;

    public PVVaultSelectionGUI(Player p) {
        this.p = p;
    }

    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        switch (e.getClick()) {
            case LEFT:
                pvVaultGUI = new PVVaultGUI(p, e.getRawSlot() + 1);
                p.openInventory(pvVaultGUI.getInventory());
                break;
            case RIGHT:
                pvIconSelectionGUI = new PVIconSelectionGUI(p);
                Core.getInstance().getEditingVault().put(p, e.getRawSlot() + 1);
                p.openInventory(pvIconSelectionGUI.getInventory());
                break;
            case MIDDLE:
                Core.getInstance().getEditingVault().put(p, e.getRawSlot() + 1);
                p.closeInventory();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VAULT_RENAME.getNode()));
                break;
        }
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {

    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(this, NeptuneAPI.getInstance().getMaxSelectionMenu(p), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("player-vaults.vault-selection.title")));
        int slot = 0;
        int vault = 1;
        while (slot < NeptuneAPI.getInstance().getMaxSelectionMenu(p)) {
            if (NeptuneAPI.getInstance().canUseVault(p, vault)) {
                gui.setItem(slot, NeptuneAPI.getInstance().vaultItem(p, vault));
            } else {
                String rawItem = Core.getInstance().getConfig().getString("player-vaults.locked-item.item");
                String[] item = rawItem.split(":");
                ItemStack stack = new ItemStack(Material.valueOf(item[0].toUpperCase()), 1, Short.parseShort(item[1]));
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("player-vaults.locked-item.name")));
                ArrayList<String> lore = new ArrayList<>();
                for (String all : Core.getInstance().getConfig().getStringList("player-vaults.locked-item.lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', all));
                }
                meta.setLore(lore);
                stack.setItemMeta(meta);
                gui.setItem(slot, stack);
            }
            slot++;
            vault++;
        }
        return gui;
    }
}
