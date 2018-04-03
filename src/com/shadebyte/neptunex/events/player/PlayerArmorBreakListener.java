package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.packet.NTitle;
import com.shadebyte.neptunex.enums.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

public class PlayerArmorBreakListener implements Listener{

    @EventHandler
    public void onArmorBreak(PlayerItemBreakEvent e) {
        Player p = e.getPlayer();
        Material item = e.getBrokenItem().getType();

        if (item == Material.LEATHER_HELMET || item == Material.CHAINMAIL_HELMET || item == Material.IRON_HELMET || item == Material.GOLD_HELMET || item == Material.DIAMOND_HELMET) {
            if (Core.getInstance().getConfig().getBoolean("armorbreak.alert-title")) {
                NTitle.getInstance().sendTitle(p, Language.ARMORBREAK_HELMET.getNode(), 20, 40, 20);
            }
        }

        if (item == Material.LEATHER_CHESTPLATE || item == Material.CHAINMAIL_CHESTPLATE || item == Material.IRON_CHESTPLATE || item == Material.GOLD_CHESTPLATE || item == Material.DIAMOND_CHESTPLATE) {
            if (Core.getInstance().getConfig().getBoolean("armorbreak.alert-title")) {
                NTitle.getInstance().sendTitle(p, Language.ARMORBREAK_CHESTPLATE.getNode(), 20, 40, 20);
            }
        }

        if (item == Material.LEATHER_LEGGINGS || item == Material.CHAINMAIL_LEGGINGS || item == Material.IRON_LEGGINGS || item == Material.GOLD_LEGGINGS || item == Material.DIAMOND_LEGGINGS) {
            if (Core.getInstance().getConfig().getBoolean("armorbreak.alert-title")) {
                NTitle.getInstance().sendTitle(p, Language.ARMORBREAK_LEGGINGS.getNode(), 20, 40, 20);
            }
        }

        if (item == Material.LEATHER_BOOTS || item == Material.CHAINMAIL_BOOTS || item == Material.IRON_BOOTS || item == Material.GOLD_BOOTS || item == Material.DIAMOND_BOOTS) {
            if (Core.getInstance().getConfig().getBoolean("armorbreak.alert-title")) {
                NTitle.getInstance().sendTitle(p, Language.ARMORBREAK_BOOTS.getNode(), 20, 40, 20);
            }
        }
    }
}
