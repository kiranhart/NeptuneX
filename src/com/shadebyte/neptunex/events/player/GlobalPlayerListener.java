package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.inventory.BotCheckInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalPlayerListener implements Listener {

    @EventHandler
    public void onKickWithAuraCheck(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if (Core.getInstance().getAuraBotCheck().contains(p)) {
            Core.getInstance().getAuraBotCheck().remove(p);
        }
    }

    @EventHandler
    public void onLeaveWithAuraCheck(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Core.getInstance().getAuraBotCheck().contains(p)) {
            Core.getInstance().getAuraBotCheck().remove(p);
        }
    }

    @EventHandler
    public void captchaCheckOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Core.getInstance().getConfig().getBoolean("botcheck.captcha-on-join")) {
            return;
        }

        Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
            Core.getInstance().getAuraBotCheck().add(p);
            p.openInventory(new BotCheckInventory().getInventory());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.BOT_CHECK_ASK.getNode()));
        }, 1);
    }
}
