package com.shadebyte.neptunex.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public interface NeptuneGUI extends InventoryHolder {

    public void onGUIClick(InventoryClickEvent e, int slot);

    public void onGUIClose(InventoryCloseEvent e);
}