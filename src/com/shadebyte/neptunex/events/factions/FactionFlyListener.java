package com.shadebyte.neptunex.events.factions;

import com.shadebyte.neptunex.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionFlyListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (!Core.getInstance().getConfig().getBoolean("faction.flight-in-own-claim")) {
            return;
        }

        if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {
            if (Core.getInstance().getFactionUtil().insideOwnClaim(p)) {
                if (!p.getAllowFlight() || !p.isFlying()) {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                }
            } else {
                if (p.isFlying()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                }
            }
        }
    }
}
