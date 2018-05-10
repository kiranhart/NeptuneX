package com.shadebyte.neptunex.events.player;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import com.shadebyte.neptunex.inventory.FreezeGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StaffModeListener implements Listener {

    private FreezeGUI freezeGUI;

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
    public void onPlayerClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {

            Player p = e.getPlayer();
            Player target = (Player) e.getRightClicked();

            if (!Core.getInstance().getStaffModePlayers().containsKey(p)) {
                return;
            }

            //Ride
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.EXAMINE))) {
                p.openInventory(target.getInventory());
            }

            //Ride
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.RIDE))) {
                target.setPassenger(p);
            }

            //Freeze
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.FREEZE_ROD))) {
                if (!Core.getInstance().getFrozenPlayers().contains(target)) {
                    Core.getInstance().getFrozenPlayers().add(target);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_FREEZER_FROZE.getNode().replace("{player}", target.getName())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_TARGET_FROZE.getNode().replace("{player}", p.getName())));
                    freezeGUI = new FreezeGUI();
                    target.openInventory(freezeGUI.getInventory());
                } else {
                    Core.getInstance().getFrozenPlayers().remove(target);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_FREEZER_UNFROZE.getNode().replace("{player}", target.getName())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.FREEZE_TARGET_UNFROZE.getNode().replace("{player}", p.getName())));
                    target.closeInventory();
                }
            }

            //CPS
            if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.CPS))) {
                if (!Core.getInstance().getClicks().containsKey(target)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCPS Check began, please wait 10 seconds for data to be collected."));
                    Core.getInstance().getClicks().put(target, 0);
                    Bukkit.getServer().getScheduler().runTaskLater(Core.getInstance(), () -> {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.CPSCHECK_RESULT.getNode().replace("{cps}", String.valueOf(Core.getInstance().getClicks().get(target) / 10)).replace("{player}", target.getName())));
                        Core.getInstance().getClicks().remove(target);
                    }, 20 * 10);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.CPSCHECK_ACTIVE.getNode()));
                }
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
            } else if (NeptuneAPI.getItemInHand(p).isSimilar(NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_OFF))) {
                Bukkit.getOnlinePlayers().forEach(all -> all.hidePlayer(p));
                p.getInventory().setItem(5, NeptuneAPI.getInstance().createStaffModeItem(NeptuneAPI.STAFF_MODE_ITEM.VANISH_ON));
                //TODO Message
            }
        }
    }

}
