package com.shadebyte.neptunex;

import com.shadebyte.neptunex.api.NEconomy;
import com.shadebyte.neptunex.api.version.SupportedPlugins;
import com.shadebyte.neptunex.commands.*;
import com.shadebyte.neptunex.enums.Language;
import com.shadebyte.neptunex.events.*;
import com.shadebyte.neptunex.events.factions.*;
import com.shadebyte.neptunex.events.gui.NeptuneGUIListener;
import com.shadebyte.neptunex.events.player.*;
import com.shadebyte.neptunex.inventory.paginatedgui.types.PaginatedGUI;
import com.shadebyte.neptunex.utils.ConfigWrapper;
import com.shadebyte.neptunex.utils.faction.FactionUUID;
import com.shadebyte.neptunex.utils.faction.FactionUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;

public class Core extends JavaPlugin {

    private static Core instance = null;
    private static Economy econ = null;
    private FactionUtil factionUtil;

    private HashSet<Player> frozenPlayers = null;
    private HashSet<Player>  auraBotCheck = null;
    private HashSet<Player> sellingChest = null;
    private HashSet<Player> usingNameTag = null;
    private HashMap<Player, PlayerInventory> staffModePlayers = null;
    private HashMap<Player, Integer> clicks = null;
    private HashMap<Player, Integer> editingVault = null;

    //Configs
    //<!---- Language File ----!>
    private static ConfigWrapper langFile;
    //<!---- Player Data File ----!>
    private static ConfigWrapper playerFile;
    //<!---- Vault Data File ----!>
    private static ConfigWrapper vaultFile;
    //<!---- Voucher Data File ----!>
    private static ConfigWrapper voucherFile;
    //<!---- Economy Data File ----!>
    private static ConfigWrapper economyFile;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        setupEconomy();

        initConfigurations();
        Language.init();

        PaginatedGUI.prepare(this);

        init();
        initEvents();
        initCommands();

        NEconomy.initializeDefaultPrices(2.5);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void initCommands() {
        getCommand("block").setExecutor(new BlockCommand());
        getCommand("ores").setExecutor(new OresCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("xpbottle").setExecutor(new XPBottleCommand());
        getCommand("staffmode").setExecutor(new StaffModeCommand());
        getCommand("cps").setExecutor(new CPSCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("playervault").setExecutor(new PlayerVaultCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("jumpboost").setExecutor(new JumpBoostCommand());
        getCommand("regen").setExecutor(new RegenCommand());
        getCommand("absorption").setExecutor(new AbsorptionCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("voucher").setExecutor(new VoucherCommand());
        getCommand("sellchest").setExecutor(new SellChestCommand());
        getCommand("genbucket").setExecutor(new GenBucketCommand());
        getCommand("captcha").setExecutor(new CaptchaCommand());
        getCommand("giveall").setExecutor(new GiveallCommand());
        getCommand("creeper").setExecutor(new GiveallCommand());
    }

    public void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new NeptuneGUIListener(), this);
        pm.registerEvents(new AutoLapisListener(), this);
        pm.registerEvents(new WithdrawListener(), this);
        pm.registerEvents(new PlayerArmorBreakListener(), this);
        pm.registerEvents(new CraftingListener(), this);
        pm.registerEvents(new MobSpawnListener(), this);
        pm.registerEvents(new StaffModeListener(), this);
        pm.registerEvents(new PlayerCPSCheckListener(), this);
        pm.registerEvents(new VaultNameListener(), this);
        pm.registerEvents(new VoucherListener(), this);
        pm.registerEvents(new CobwebLimiterListener(), this);
        pm.registerEvents(new EconomyListener(), this);
        pm.registerEvents(new GenBucketListener(), this);
        pm.registerEvents(new CooldownsListener(), this);
        pm.registerEvents(new GlobalPlayerListener(), this);
        pm.registerEvents(new FactionFlyListener(), this);
        pm.registerEvents(new FactionLocationListener(), this);
        pm.registerEvents(new NameTagListener(), this);
        pm.registerEvents(new PlayerCreeperDamageListener(), this);
    }

    private void initConfigurations() {
        langFile = new ConfigWrapper(this, "", "Lang.yml");
        playerFile = new ConfigWrapper(this, "", "PlayerData.yml");
        vaultFile = new ConfigWrapper(this, "", "VaultData.yml");
        voucherFile = new ConfigWrapper(this, "", "VoucherData.yml");
        economyFile = new ConfigWrapper(this, "", "Economy.yml");

        saveConfigFiles();
    }

    /**
     * Getter for the variable 'econ'
     *
     * @return econ
     */
    public static Economy getEcon() {
        return econ;
    }

    /**
     * Handy method to check if a plugin is loaded on the server.
     */
    public boolean pluginIsLoaded(SupportedPlugins plugin) {
        if (getServer().getPluginManager().getPlugin(plugin.getPlugin()) == null) {
            return false;
        }
        return true;
    }

    /**
     * Method to check whether or not Vault is installed
     * if it is, then initialize the economy, else do not.
     */
    private boolean setupEconomy() {
        if (!pluginIsLoaded(SupportedPlugins.VAULT)) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void init() {
        frozenPlayers = new HashSet<>();
        staffModePlayers = new HashMap<>();
        clicks = new HashMap<>();
        editingVault = new HashMap<>();
        sellingChest = new HashSet<>();
        auraBotCheck = new HashSet<>();
        usingNameTag = new HashSet<>();

        factionUtil = new FactionUUID();
    }

    private void saveConfigFiles() {
        langFile.saveConfig();
        playerFile.saveConfig();
        vaultFile.saveConfig();
        voucherFile.saveConfig();
        economyFile.saveConfig();
    }

    public static ConfigWrapper getPlayerFile() {
        return playerFile;
    }

    public static Core getInstance() {
        return instance;
    }

    public static ConfigWrapper getLangFile() {
        return langFile;
    }

    public HashSet<Player> getFrozenPlayers() {
        return frozenPlayers;
    }

    public HashMap<Player, PlayerInventory> getStaffModePlayers() {
        return staffModePlayers;
    }

    public HashMap<Player, Integer> getEditingVault() {
        return editingVault;
    }

    public static ConfigWrapper getVaultFile() {
        return vaultFile;
    }

    public static ConfigWrapper getVoucherFile() {
        return voucherFile;
    }

    public static ConfigWrapper getEconomyFile() {
        return economyFile;
    }

    public HashMap<Player, Integer> getClicks() {
        return clicks;
    }

    public HashSet<Player> getSellingChest() {
        return sellingChest;
    }

    public HashSet<Player> getAuraBotCheck() {
        return auraBotCheck;
    }

    public HashSet<Player> getUsingNameTag() {
        return usingNameTag;
    }

    public FactionUtil getFactionUtil() {
        return factionUtil;
    }
}
