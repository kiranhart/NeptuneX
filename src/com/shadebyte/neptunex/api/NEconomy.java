package com.shadebyte.neptunex.api;

import com.shadebyte.neptunex.Core;
import org.bukkit.Material;

public class NEconomy {

    private static NEconomy instance;

    private double itemPrice;
    private String item;

    private NEconomy(String item) {
        this.item = item;
    }

    public static NEconomy getInstance(String item) {
        if (instance == null) {
            instance = new NEconomy(item);
        }
        return instance;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        Core.getEconomyFile().getConfig().set("prices." + item, itemPrice);
        Core.getEconomyFile().saveConfig();
    }

    public boolean priceExist() {
        if (Core.getEconomyFile().getConfig().contains("prices." + item)) {
            return true;
        }
        return false;
    }

    public static void initializeDefaultPrices(double price) {
        for (Material material : Material.values()) {
            String name = material.name();
            name += ":0";
            if (!Core.getEconomyFile().getConfig().contains("prices." + name)) {
                Core.getEconomyFile().getConfig().set("prices." + name, price);
            }
        }
        Core.getEconomyFile().saveConfig();
    }
}
