package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.NeptuneAPI;
import com.shadebyte.neptunex.enums.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameTagListener implements Listener{

    @EventHandler
    public void onNameTagUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (!NeptuneAPI.getInstance().isItemTag(NeptuneAPI.getItemInHand(p))) {
                return;
            }

            if (p.getItemInHand().getAmount() >= 2) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                p.updateInventory();
                checkIfUsing(p);
            } else if (p.getItemInHand().getAmount() == 1) {
                p.setItemInHand(null);
                p.updateInventory();
                checkIfUsing(p);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Core.getInstance().getUsingNameTag().contains(p)) {
            p.getInventory().addItem(NeptuneAPI.getInstance().nametag());
            Core.getInstance().getUsingNameTag().remove(p);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!Core.getInstance().getUsingNameTag().contains(p)) {
            return;
        }

        if (e.getMessage().equalsIgnoreCase(Core.getInstance().getConfig().getString("nametags.cancel-word"))) {
            p.getInventory().addItem(NeptuneAPI.getInstance().nametag());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_CANCELED.getNode()));
            Core.getInstance().getUsingNameTag().remove(p);
            e.setCancelled(true);
            return;
        }

        for (String blockedWords : Core.getInstance().getConfig().getStringList("nametags.blocked-words")) {
            if (e.getMessage().toLowerCase().contains(blockedWords.toLowerCase())) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat word is blacklisted!"));
                e.setCancelled(true);
                return;
            }
        }

        if (NeptuneAPI.getItemInHand(p).getType() == Material.AIR || NeptuneAPI.getItemInHand(p) == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NOTHING_IN_HAND.getNode()));
            e.setCancelled(true);
            return;
        }

        for (String blockedItems : Core.getInstance().getConfig().getStringList("nametags.blocked-items")) {
            String blockedItemsArr[] = blockedItems.split(":");
            String ID = blockedItemsArr[0];
            short DATA = Short.parseShort(blockedItemsArr[1]);
            if(NeptuneAPI.getItemInHand(p).getType().name().equalsIgnoreCase(ID) && NeptuneAPI.getItemInHand(p).getDurability() == DATA) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NAMETAG_BLOCKED.getNode()));
                e.setCancelled(true);
                return;
            }
        }

        ItemStack item = NeptuneAPI.getItemInHand(p);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        e.setCancelled(true);
        item.setItemMeta(itemMeta);
        p.updateInventory();
        Core.getInstance().getUsingNameTag().remove(p);
    }

    private void sendInstructions(Player p) {
        for (String all : Core.getInstance().getConfig().getStringList("nametags.instructions")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', all));
        }
    }

    private void checkIfUsing(Player p) {
        if (Core.getInstance().getUsingNameTag().contains(p)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAlready using nametag!"));
            return;
        }
        Core.getInstance().getUsingNameTag().add(p);
        sendInstructions(p);
    }

}
