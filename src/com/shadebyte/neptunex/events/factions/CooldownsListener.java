package com.shadebyte.neptunex.events.factions;

import com.shadebyte.neptunex.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CooldownsListener implements Listener {

    /**
     * =============== Golden Apple Cool-downs =================
     */

    @EventHandler
    public void onGoldenAppleCooldown(PlayerItemConsumeEvent e) {

        if (!Core.getInstance().getConfig().getBoolean("cooldowns.golden-apple.enabled")) {
            return;
        }

        if (e.getItem().getType() != Material.GOLDEN_APPLE && e.getItem().getDurability() != 0) {
            return;
        }

        Player p = e.getPlayer();

        if (!Core.getPlayerFile().getConfig().contains("players." + p.getUniqueId().toString() + ".cooldowns.goldenapple")) {
            Core.getPlayerFile().getConfig().set("players." + p.getUniqueId().toString() + ".cooldowns.goldenapple", System.currentTimeMillis());
            Core.getPlayerFile().saveConfig();
            return;
        }

        long storedTime = Core.getPlayerFile().getConfig().getLong("players." + p.getUniqueId().toString() + ".cooldowns.goldenapple");
        int waitTime = Core.getInstance().getConfig().getInt("cooldowns.golden-apple.time");
    }
}
