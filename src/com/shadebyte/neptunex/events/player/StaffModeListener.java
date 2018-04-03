package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StaffModeListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() instanceof PlayerInventory) {
            if (Core.getInstance().getStaffModePlayers().get(p) != null) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onStaffModeItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            //Launch Compass
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.LAUNCH_COMPASS))) {
                p.setVelocity(p.getLocation().getDirection().normalize().multiply(2.5));
            }

            //Teleport
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.TELEPORT))) {
                List<Player> all = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission(Permissions.STAFF_PERM.getNode())) {
                        continue;
                    }
                    all.add(player);
                }
                p.teleport(all.get(ThreadLocalRandom.current().nextInt(all.size())));
                all.clear();
            }

            //Vanish
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_ON))) {
                Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(p));
                p.getInventory().setItem(5, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_OFF));
                //TODO Message
            } else if(NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_OFF))) {
                Bukkit.getOnlinePlayers().forEach(all -> all.hidePlayer(p));
                p.getInventory().setItem(5, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_ON));
                //TODO Message
            }
        }
    }

}
