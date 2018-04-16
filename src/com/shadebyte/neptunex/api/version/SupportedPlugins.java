package com.shadebyte.neptunex.api.version;

public enum SupportedPlugins {

    VAULT("Vault"),
    FACTIONS("Factions");

    private String plugin;

    SupportedPlugins(String plugin) {
        this.plugin = plugin;
    }

    public String getPlugin() {
        return plugin;
    }
}
