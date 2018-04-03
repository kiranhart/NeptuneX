package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NEconomy;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EconomyListener implements Listener {

    @EventHandler
    public void onChestSell(PlayerInteractEvent e) {

        if (!Core.getInstance().getConfig().getBoolean("sellchest.enabled")) {
            return;
        }

        if (e.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (!Core.getInstance().getSellingChest().contains(e.getPlayer())) {
            return;
        }

        if (e.getClickedBlock().getState() instanceof Chest) {
            Chest chest = (Chest) e.getClickedBlock().getState();
            if (chest.getInventory().getContents().length != 0) {

                double payment = 0.0;

                for (ItemStack itemStack : chest.getInventory().getContents()) {
                    if (itemStack == null || itemStack.getType() == Material.AIR) {
                        continue;
                    }

                    String item = itemStack.getType().name() + ":" + itemStack.getDurability();
                    if (Core.getEconomyFile().getConfig().contains("prices." + item)) {
                        payment += Core.getEconomyFile().getConfig().getDouble("prices." + item) * itemStack.getAmount();
                        chest.getInventory().remove(itemStack);
                    }
                }

                Core.getEcon().depositPlayer(e.getPlayer(), payment);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Language.SELL_CHEST_SOLD.getNode().replace("{price}", NeptuneAPI.getInstance().formatNumber(payment))));
            }
        }

        Core.getInstance().getSellingChest().remove(e.getPlayer());
    }

}
