package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerCPSCheckListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (Core.getInstance().getClicks().containsKey(p)) {
                Core.getInstance().getClicks().put(p, Core.getInstance().getClicks().get(p) + 1);
            }
        }
    }
}
