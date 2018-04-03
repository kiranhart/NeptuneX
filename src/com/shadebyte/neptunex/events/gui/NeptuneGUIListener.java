package com.shadebyte.neptunex.events.gui;

import com.shadebyte.neptunex.inventory.FreezeGUI;
import com.shadebyte.neptunex.inventory.NeptuneGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class NeptuneGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof NeptuneGUI) {
            NeptuneGUI gui = (NeptuneGUI) e.getInventory().getHolder();
            gui.onGUIClick(e, e.getRawSlot());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof NeptuneGUI) {
            NeptuneGUI gui = (NeptuneGUI) e.getInventory().getHolder();
            gui.onGUIClose(e);
        }
    }
}
