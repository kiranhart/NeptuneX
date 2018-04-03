package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

    @EventHandler
    public void onDisabledItemCraftEvent(PrepareItemCraftEvent e) {

        String itemID = "";
        short itemData = 0;

        for (String items : Core.getInstance().getConfig().getStringList("disabled-crafting")) {

            String[] stringSplit = items.split(":");

            itemID = stringSplit[0];
            itemData = Short.parseShort(stringSplit[1]);

            if (e.getRecipe().getResult().getType() == Material.valueOf(itemID) && e.getRecipe().getResult().getDurability() == itemData) {
                for (HumanEntity p : e.getViewers()) {
                    if (!p.hasPermission(Permissions.CRAFTING_BYPASS.getNode())) {
                        e.getInventory().setResult(new ItemStack(Material.AIR));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.ITEM_BLOCKED.getNode()));
                    }
                }
            }
        }
    }
}
