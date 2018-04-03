package com.shadebyte.neptunex.inventory.playervault;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.inventory.NeptuneGUI;
import org.apache.commons.lang.WordUtils;
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
import java.util.List;

public class PVIconSelectionGUI implements NeptuneGUI {

    private Player p;
    private PVVaultSelectionGUI pvVaultSelectionGUI;

    public PVIconSelectionGUI(Player p) {
        this. p = p;
    }

    @Override
    public void onGUIClick(InventoryClickEvent e, int slot) {
        e.setCancelled(true);
        ItemStack is = e.getCurrentItem();
        int vault = Core.getInstance().getEditingVault().get(p);
        String item = String.valueOf(is.getType().name() + ":" + is.getDurability());
        String name = Core.getVaultFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + vault + ".name");

        Core.getVaultFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".icon", item);
        Core.getVaultFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".name", name);
        Core.getVaultFile().saveConfig();
        Core.getInstance().getEditingVault().remove(p);
        pvVaultSelectionGUI = new PVVaultSelectionGUI(p);
        p.openInventory(pvVaultSelectionGUI.getInventory());
    }

    @Override
    public void onGUIClose(InventoryCloseEvent e) {
        Core.getInstance().getEditingVault().remove(p);
    }

    @Override
    public Inventory getInventory() {
        Inventory gui = Bukkit.createInventory(this, Core.getInstance().getConfig().getInt("player-vaults.icon-selection.size"), ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("player-vaults.icon-selection.title")));
        for (String nodes : Core.getInstance().getConfig().getConfigurationSection("player-vaults.icon-selection.data").getKeys(false)) {
            String[] item = Core.getInstance().getConfig().getString("player-vaults.icon-selection.data." + nodes + ".item").split(":");
            ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + WordUtils.capitalizeFully(stack.getType().name().replace("_", " "))));
            List<String> lore = new ArrayList<>();
            for (String s : Core.getInstance().getConfig().getStringList("player-vaults.icon-selection.data." + nodes + ".lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
            gui.setItem(Integer.valueOf(nodes) - 1, stack);
        }
        return gui;
    }
}
