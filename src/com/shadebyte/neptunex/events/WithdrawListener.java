package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.utils.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WithdrawListener implements Listener {

    @EventHandler
    public void onBanknoteRedeem(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        Player p = e.getPlayer();
        if (NeptuneAPI.getItemInHand(p) == null || NeptuneAPI.getItemInHand(p).getType() == Material.AIR) {
            return;
        }

        if (NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "BankNoteValue") == null) {
            return;
        }

        int value = (int) NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "BankNoteValue");
        Core.getEcon().depositPlayer(p, value);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.REDEEM_MONEY.getNode().replace("{value}", NeptuneAPI.getInstance().formatNumber(value))));

        if (NeptuneAPI.getItemInHand(p).getAmount() >= 2) {
            NeptuneAPI.getItemInHand(p).setAmount(NeptuneAPI.getItemInHand(p).getAmount() - 1);
            p.updateInventory();
        } else {
            NeptuneAPI.setItemInHand(p, null);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onXPBottleRedeem(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (NeptuneAPI.getItemInHand(p) == null || NeptuneAPI.getItemInHand(p).getType() == Material.AIR) {
            return;
        }

        if (NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "XPBottleValue") == null) {
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (e.getAction() != Action.RIGHT_CLICK_AIR) {
            e.setCancelled(true);
        }

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
        }

        int value = (int) NBTEditor.getItemTag(NeptuneAPI.getItemInHand(p), "XPBottleValue");
        int exp = NeptuneAPI.getInstance().getXp(p);
        exp += value;

        p.setTotalExperience(0);
        p.setLevel(0);
        p.setExp(0.0F);
        p.giveExp(exp);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.REDEEM_EXP.getNode().replace("{value}", NeptuneAPI.getInstance().formatNumber(value))));

        if (NeptuneAPI.getItemInHand(p).getAmount() >= 2) {
            NeptuneAPI.getItemInHand(p).setAmount(NeptuneAPI.getItemInHand(p).getAmount() - 1);
            p.updateInventory();
        } else {
            NeptuneAPI.setItemInHand(p, null);
            p.updateInventory();
        }
    }
}
