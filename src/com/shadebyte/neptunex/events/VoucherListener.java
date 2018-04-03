package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.utils.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class VoucherListener implements Listener {

    @EventHandler
    public void onVoucherClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        Player p = e.getPlayer();
        if (NeptuneAPI.getItemInHand(p) == null || NeptuneAPI.getItemInHand(p).getType() == Material.AIR) {
            return;
        }

        if (NeptuneAPI.getInstance().isVoucher(NeptuneAPI.getItemInHand(p))) {
            //Check if voucher is in config
            String voucherID = (String) NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "NeptuneVoucherID");
            if (!Core.getVoucherFile().getConfig().contains("vouchers." + voucherID)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAn error has occurred, that voucher is invalid"));
                return;
            }

            for (String cmds : Core.getVoucherFile().getConfig().getStringList("vouchers." + voucherID + ".commands")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("{player}", p.getName()));
            }

            if (NeptuneAPI.getItemInHand(p).getAmount() >= 2) {
                NeptuneAPI.getItemInHand(p).setAmount(NeptuneAPI.getItemInHand(p).getAmount() - 1);
            } else {
                NeptuneAPI.setItemInHand(p, null);
            }
        }
    }
}
