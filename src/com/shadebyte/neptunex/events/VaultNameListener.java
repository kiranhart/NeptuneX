package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.inventory.playervault.PVVaultSelectionGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class VaultNameListener implements Listener {

    private PVVaultSelectionGUI pvVaultSelectionGUI;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!Core.getInstance().getEditingVault().containsKey(p)) {
            return;
        }
        e.setCancelled(true);
        pvVaultSelectionGUI = new PVVaultSelectionGUI(p);

        if (e.getMessage().equalsIgnoreCase(Core.getInstance().getConfig().getString("player-vaults.cancel-word"))) {
            Core.getInstance().getEditingVault().remove(p);
            p.openInventory(pvVaultSelectionGUI.getInventory());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VAULT_NAMING_CANCELLED.getNode()));
            return;
        }

        int vault = Core.getInstance().getEditingVault().get(p);
        String msg = e.getMessage();
        Core.getVaultFile().getConfig().set("players." + p.getUniqueId().toString() + "." + vault + ".name", msg);
        Core.getVaultFile().saveConfig();
        Core.getInstance().getEditingVault().remove(p);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.VAULT_RENAMED.getNode().replace("{name}", msg).replace("{vault_number}", String.valueOf(vault))));
        p.openInventory(pvVaultSelectionGUI.getInventory());

    }
}
