package com.shadebyte.neptunex.events.factions;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CobwebLimiterListener implements Listener {

    @EventHandler
    public void onCobWebPlace(BlockPlaceEvent e) {

        if (!Core.getInstance().getConfig().getBoolean("limit-cobweb-on-y.enabled")) {
            return;
        }

        if (e.getBlock().getType() != Material.WEB) {
            return;
        }

        int totalCobWebs = 0;

        Block placedAgainst = e.getBlockAgainst();
        Location loc = placedAgainst.getLocation();
        loc.setY(0);

        while (loc.getY() != 256) {
            if (loc.getBlock().getType() == Material.WEB) {
                totalCobWebs += 1;
            }
            loc.setY(loc.getY() + 1);
        }

        if (totalCobWebs > Core.getInstance().getConfig().getInt("limit-cobweb-on-y.max-cobweb-per-y")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Language.COBWEB_LIMIT.getNode().replace("{amount}", String.valueOf(Core.getInstance().getConfig().getInt("limit-cobweb-on-y.max-cobweb-per-y")))));
        }
    }
}
