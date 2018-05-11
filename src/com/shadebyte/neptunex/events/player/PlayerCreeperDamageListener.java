package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class PlayerCreeperDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamageOtherPlayerCreeper(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Creeper)) {
            return;
        }

        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Creeper creeper = (Creeper) e.getEntity();
        Player p = (Player) e.getDamager();

        if (!creeper.hasMetadata("owner")) {
            return;
        }

        String uuid = "";

        exit:
        for (MetadataValue v : creeper.getMetadata("owner")) {
            if (p.getUniqueId().toString().equals(v.toString())) {
                uuid = v.asString();
                break exit;
            }
        }
        e.setCancelled(true);
    }
}
