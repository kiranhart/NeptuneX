package com.shadebyte.neptunex.events.factions;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.shadebyte.neptunex.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionLocationListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (e.getTo().getX() == e.getFrom().getX() || e.getTo().getZ() == e.getFrom().getZ()) {
            return;
        }

        Player p = e.getPlayer();

        Faction oldFaction = Board.getInstance().getFactionAt(new FLocation(e.getFrom()));
        Faction newFaction = Board.getInstance().getFactionAt(new FLocation(e.getTo()));

        if (oldFaction != newFaction) {

            if (Core.getInstance().getConfig().getBoolean("faction.enter-msg.enabled")) {
                for (String s : Core.getInstance().getConfig().getStringList("faction.enter-msg.msg")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replace("{faction_desc}", newFaction.getDescription()).replace("{faction_name}", newFaction.getTag())));
                }
            }

            if (Core.getInstance().getConfig().getBoolean("faction.leave-msg.enabled")) {
                for (String s : Core.getInstance().getConfig().getStringList("faction.leave-msg.msg")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replace("{faction_desc}", newFaction.getDescription()).replace("{faction_name}", newFaction.getTag())));
                }
            }
        }
    }
}
