package com.shadebyte.neptunex.events.factions;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.utils.NBTEditor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GenBucketListener implements Listener {

    @EventHandler
    public void onVoucherClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = e.getPlayer();
        if (NeptuneAPI.getItemInHand(p) == null || NeptuneAPI.getItemInHand(p).getType() == Material.AIR) {
            return;
        }

        if (NeptuneAPI.getInstance().isGenBucket(NeptuneAPI.getItemInHand(p))) {
            e.setCancelled(true);

            String bucketMaterial = (String) NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "GenBucketMaterial");
            Material mat = Material.valueOf(bucketMaterial.toUpperCase());
            Location location = e.getClickedBlock().getLocation();
            location.setY(location.getY() - 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (location.getBlock().getType() != Material.AIR) {
                        this.cancel();
                        
                    } else {
                        location.getBlock().setType(mat);
                        location.setY(location.getY() - 1);
                    }
                }
            }.runTaskTimer(Core.getInstance(), 0, 10);

            if (NeptuneAPI.getItemInHand(p).getAmount() >= 2) {
                NeptuneAPI.getItemInHand(p).setAmount(NeptuneAPI.getItemInHand(p).getAmount() - 1);
            } else {
                NeptuneAPI.setItemInHand(p, null);
            }
        }
    }
}
