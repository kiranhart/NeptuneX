package com.shadebyte.neptunex.api;

import com.shadebyte.neptunex.Core;
import com.shadebyte.neptunex.api.version.Version;
import com.shadebyte.neptunex.utils.NBTEditor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeptuneAPI {

    private static NeptuneAPI instance;

    public enum STAFF_MODE_ITEM {
        LAUNCH_COMPASS("launch-compass"), TELEPORT("teleport"), VANISH_ON("vanish-on"), VANISH_OFF("vanish-off"), CPS("cps"), FREEZE_ROD("freeze-rod"), EXAMINE("examine"), RIDE("ride");
        private String node;

        STAFF_MODE_ITEM(String node) {
            this.node = node;
        }

        public String getNode() {
            return node;
        }
    }

    private double xplevel;
    private int xp, result;

    private NeptuneAPI() {
    }

    public static NeptuneAPI getInstance() {
        if (instance == null) {
            instance = new NeptuneAPI();
        }
        return instance;
    }


    public ItemStack nametag() {
        ItemStack is = new ItemStack(Material.valueOf(Core.getInstance().getConfig().getString("nametags.item")), 1, (short) Core.getInstance().getConfig().getInt("nametags.data"));
        ItemMeta ismeta = is.getItemMeta();
        ismeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("nametags.name")));
        ArrayList<String> lore = new ArrayList<>();
        for (String all : Core.getInstance().getConfig().getStringList("nametags.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }
        ismeta.setLore(lore);
        is.setItemMeta(ismeta);
        is = NBTEditor.setItemTag(is, "Valid", "NeptuneItemTag");
        return is;
    }

    public boolean isItemTag(ItemStack is) {
        if (NBTEditor.getItemTag(is, "NeptuneItemTag") != null) {
            return true;
        }
        return false;
    }

    public void setXpLevel(int level, float cExp) {
        if (level > 30) {
            xplevel = 4.5D * (double) level * (double) level - 162.5D * (double) level + 2220.0D;
            xp = 9 * level - 158;
            xplevel += (double) Math.round(cExp * (float) xp);
            result = (int) xplevel;
        } else if (level > 15) {
            xplevel = 2.5D * (double) level * (double) level - 40.5D * (double) level + 360.0D;
            xp = 5 * level - 38;
            xplevel += (double) Math.round(cExp * (float) xp);
            result = (int) xplevel;
        } else if (level <= 15) {
            xplevel = (double) (level * level + 6 * level);
            xp = 2 * level + 7;
            xplevel += (double) Math.round(cExp * (float) xp);
            result = (int) xplevel;
        }
    }

    public int getXp(Player p) {
        setXpLevel(p.getLevel(), p.getExp());
        return result;
    }

    /**
     * Method to check if a command sender is a player
     */
    public boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public int getAmount(Player player, int id) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] items = inventory.getContents();
        int has = 0;
        for (ItemStack item : items) {
            if ((item != null) && (item.getTypeId() == id) && (item.getAmount() > 0)) {
                has += item.getAmount();
            }
        }
        return has;
    }

    public ItemStack createConfigItem(String node) {
        String[] item = Core.getInstance().getConfig().getString(node + ".item").split(":");
        ItemStack is = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString(node + ".name")));
        List<String> lore = new ArrayList<>();
        for (String all : Core.getInstance().getConfig().getStringList(node + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }
        meta.setLore(lore);
        is.setItemMeta(meta);
        return is;
    }

    public ItemStack createOreGUIItem(Player p, Material mat, int slot) {
        ItemStack is = createConfigItem("guis.ores.slots." + slot);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(meta.getDisplayName().replace("{amount}", String.valueOf(p.getStatistic(Statistic.MINE_BLOCK, mat))));
        List<String> lore = meta.getLore();
        List<String> newLore = new ArrayList<>();
        for (String all : lore) {
            all.replace("{amount}", String.valueOf(p.getStatistic(Statistic.MINE_BLOCK, mat)));
            newLore.add(all);
        }
        meta.setLore(newLore);
        is.setItemMeta(meta);
        return is;
    }

    /**
     * Method to check if a given string value is
     * only filled with integers
     */
    public boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Format a number, this is used to make the
     * number easier to read by adding commas
     */
    public String formatNumber(int number) {
        return String.valueOf(NumberFormat.getInstance().format(number));
    }

    public String formatNumber(double number) {
        return String.valueOf(NumberFormat.getInstance().format(number));
    }

    /**
     * Method to check if the player has an
     * available inventory slot.
     */
    public boolean hasAvaliableSlot(Player player) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Non-Version dependant method to
     * get the item stack from the player hand.
     */
    @SuppressWarnings("deprecation")
    public static ItemStack getItemInHand(Player player) {
        if (Version.getVersion().getVersionInteger() >= 191) {
            return player.getInventory().getItemInMainHand();
        } else {
            return player.getItemInHand();
        }
    }

    /**
     * Non-Version dependant method to
     * set the item stack from the player hand.
     */
    @SuppressWarnings("deprecation")
    public static void setItemInHand(Player player, ItemStack item) {
        if (Version.getVersion().getVersionInteger() >= 191) {
            player.getInventory().setItemInMainHand(item);
        } else {
            player.setItemInHand(item);
        }
    }

    /**
     * Method used to create a new xp bottle signed by a player
     */
    public ItemStack createPlayerXPBottle(Player withdrawer, int value) {
        String item[] = Core.getInstance().getConfig().getString("xpbottle.signed.item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("xpbottle.signed.name")));
        List<String> lore = new ArrayList();
        for (String s : Core.getInstance().getConfig().getStringList("xpbottle.signed.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{signer}", withdrawer.getName()).replace("{value}", formatNumber(value))));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.setItemTag(stack, value, "XPBottleValue");
        return stack;
    }

    /**
     * Method used to create a new xp bottle signed by a player
     */
    public ItemStack createAdminXPBottle(int value) {
        String item[] = Core.getInstance().getConfig().getString("xpbottle.admin.item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("xpbottle.admin.name")));
        List<String> lore = new ArrayList();
        for (String s : Core.getInstance().getConfig().getStringList("xpbottle.admin.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{value}", formatNumber(value))));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.setItemTag(stack, value, "XPBottleValue");
        return stack;
    }

    /**
     * Method used to create a new banknote signed by a player
     */
    public ItemStack createPlayerBanknote(Player withdrawer, int value) {
        String item[] = Core.getInstance().getConfig().getString("banknote.signed.item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("banknote.signed.name")));
        List<String> lore = new ArrayList();
        for (String s : Core.getInstance().getConfig().getStringList("banknote.signed.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{signer}", withdrawer.getName()).replace("{value}", formatNumber(value))));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.setItemTag(stack, value, "BankNoteValue");
        return stack;
    }

    /**
     * Method used to create a new banknote signed by server
     */
    public ItemStack createAdminBanknote(int value) {
        String item[] = Core.getInstance().getConfig().getString("banknote.admin.item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("banknote.admin.name")));
        List<String> lore = new ArrayList();
        for (String s : Core.getInstance().getConfig().getStringList("banknote.admin.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{value}", formatNumber(value))));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.setItemTag(stack, value, "BankNoteValue");
        return stack;
    }

    /**
     * Create a new ItemStack for the Staff mode slots
     */
    public ItemStack createStaffModeItem(STAFF_MODE_ITEM staffItem) {
        String[] item = Core.getInstance().getConfig().getString("staff-mode.slot-item." + staffItem.getNode() + ".item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("staff-mode.slot-item." + staffItem.getNode() + ".name")));
        List<String> lore = new ArrayList<>();
        for (String all : Core.getInstance().getConfig().getStringList("staff-mode.slot-item." + staffItem.getNode() + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Check the maximum size vault a player can have.
     */
    public int getMaxSize(Player p) {
        int size = Core.getInstance().getConfig().getInt("player-vaults.default-vault-size");
        if (p.hasPermission("NeptuneX.pv.size.9")) {
            size = 9;
        }
        if (p.hasPermission("NeptuneX.pv.size.18")) {
            size = 18;
        }
        if (p.hasPermission("NeptuneX.pv.size.27")) {
            size = 27;
        }
        if (p.hasPermission("NeptuneX.pv.size.36")) {
            size = 36;
        }
        if (p.hasPermission("NeptuneX.pv.size.45")) {
            size = 45;
        }
        if (p.hasPermission("NeptuneX.pv.size.54")) {
            size = 54;
        }
        return size;
    }

    /**
     * Check the maximum size vault a player can have.
     */
    public int getMaxSelectionMenu(Player p) {
        int size = Core.getInstance().getConfig().getInt("player-vaults.default-select-menu-size");
        if (p.hasPermission("NeptuneX.pv.selectionsize.9")) {
            size = 9;
        }
        if (p.hasPermission("NeptuneX.pv.selectionsize.18")) {
            size = 18;
        }
        if (p.hasPermission("NeptuneX.pv.selectionsize.27")) {
            size = 27;
        }
        if (p.hasPermission("NeptuneX.pv.selectionsize.36")) {
            size = 36;
        }
        if (p.hasPermission("NeptuneX.pv.selectionsize.45")) {
            size = 45;
        }
        if (p.hasPermission("NeptuneX.pv.selectionsize.54")) {
            size = 54;
        }
        return size;
    }

    /**
     * Check if the player can use the vault.
     */
    public boolean canUseVault(Player p, int number) {
        if (p.hasPermission("NeptuneX.pv.amt." + String.valueOf(number))) {
            return true;
        }
        for (int x = number; x <= 99; x++) {
            if (p.hasPermission("NeptuneX.pv.amt." + String.valueOf(x))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the vault item for the selection
     */
    public ItemStack vaultItem(Player p, int vault) {
        String itemRaw;
        if (Core.getVaultFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault)) {
            itemRaw = Core.getVaultFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + vault + ".icon");
        } else {
            itemRaw = Core.getInstance().getConfig().getString("player-vaults.vault-selection.default-item");
        }
        String[] item = itemRaw.split(":");
        ItemStack is = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = is.getItemMeta();
        if (Core.getVaultFile().getConfig().contains("players." + p.getUniqueId().toString() + "." + vault)) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getVaultFile().getConfig().getString("players." + p.getUniqueId().toString() + "." + vault + ".name")));
        } else {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("player-vaults.vault-selection.default-item-name").replace("{vaultnumber}", String.valueOf(vault))));
        }
        ArrayList<String> lore = new ArrayList<>();
        for (String all : Core.getInstance().getConfig().getStringList("player-vaults.vault-selection.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all.replace("{vaultnumber}", String.valueOf(vault))));
        }
        meta.setLore(lore);
        is.setItemMeta(meta);
        return is;
    }

    public void registerNewVoucher(String id) {
        Core.getVoucherFile().getConfig().set("vouchers." + id + ".name", "&6&l" + StringUtils.capitalize(id) + " &e&lVoucher");
        Core.getVoucherFile().getConfig().set("vouchers." + id + ".item", "PAPER:0");
        Core.getVoucherFile().getConfig().set("vouchers." + id + ".glow", false);
        Core.getVoucherFile().getConfig().set("vouchers." + id + ".lore", Arrays.asList("&7An Example Voucher lore", "&7Change this within the voucher file."));
        Core.getVoucherFile().getConfig().set("vouchers." + id + ".commands", Arrays.asList("broadcast &6Hi {player}", "give {player} cake 1"));
        Core.getVoucherFile().saveConfig();
    }

    public void unregisterNewVoucher(String id) {
        Core.getVoucherFile().getConfig().set("vouchers." + id, null);
        Core.getVoucherFile().saveConfig();
    }

    public ItemStack createVoucher(String id) {
        String[] item = Core.getVoucherFile().getConfig().getString("vouchers." + id + ".item").split(":");
        ItemStack is = new ItemStack(Material.valueOf(item[0].toUpperCase()), 1, Short.parseShort(item[1]));
        if (Core.getVoucherFile().getConfig().getBoolean("vouchers." + id + ".glow")) {
            is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        }
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getVoucherFile().getConfig().getString("vouchers." + id + ".name")));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        for (String all : Core.getVoucherFile().getConfig().getStringList("vouchers." + id + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }
        meta.setLore(lore);
        is.setItemMeta(meta);

        is = NBTEditor.setItemTag(is, id, "NeptuneVoucherID");
        return is;
    }

    public boolean isVoucher(ItemStack stack) {
        if (NBTEditor.getItemTag(stack, "NeptuneVoucherID") != null) {
            return true;
        }
        return false;
    }

    public ItemStack createGenBucket(Material material) {
        String item[] = Core.getInstance().getConfig().getString("genbuckets.item").split(":");
        ItemStack stack = new ItemStack(Material.valueOf(item[0]), 1, Short.parseShort(item[1]));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Core.getInstance().getConfig().getString("genbuckets.name")));
        List<String> lore = new ArrayList();
        for (String s : Core.getInstance().getConfig().getStringList("genbuckets.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{material}", StringUtils.capitalize(material.name().replace("_", " ")))));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        stack = NBTEditor.setItemTag(stack, material.name(), "GenBucketMaterial");
        return stack;
    }

    public boolean isGenBucket(ItemStack stack) {
        if (NBTEditor.getItemTag(stack, "GenBucketMaterial") != null) {
            return true;
        }
        return false;
    }
}
