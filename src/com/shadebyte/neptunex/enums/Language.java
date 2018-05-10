package com.shadebyte.neptunex.enums;

import com.shadebyte.neptunex.Core;

public enum Language {

    NO_PERMISSION(Core.getLangFile().getConfig().getString("no-perm")),

    PLAYER_OFFLINE(Core.getLangFile().getConfig().getString("player-offline")),
    NOTHING_IN_HAND(Core.getLangFile().getConfig().getString("nothing-in-hand")),
    PLAYERS_ONLY(Core.getLangFile().getConfig().getString("players-only")),
    ITEM_BLOCKED(Core.getLangFile().getConfig().getString("item-blocked")),
    NOT_A_NUMBER(Core.getLangFile().getConfig().getString("not-a-number")),
    INGOT_CONVERT(Core.getLangFile().getConfig().getString("ingot-convert")),
    CHAT_MUTED(Core.getLangFile().getConfig().getString("chat.mute")),
    CHAT_UNMUTED(Core.getLangFile().getConfig().getString("chat.unmute")),
    FREEZE_FREEZER_FROZE(Core.getLangFile().getConfig().getString("freeze.freezer-freeze")),
    FREEZE_FREEZER_UNFROZE(Core.getLangFile().getConfig().getString("freeze.freezer-unfreeze")),
    FREEZE_TARGET_FROZE(Core.getLangFile().getConfig().getString("freeze.target-freeze")),
    FREEZE_TARGET_UNFROZE(Core.getLangFile().getConfig().getString("freeze.target-unfreeze")),

    NOT_ENOUGH_MONEY(Core.getLangFile().getConfig().getString("not-enough-money")),
    NOT_ENOUGH_EXP(Core.getLangFile().getConfig().getString("not-enough-exp")),
    WITHDRAW_EXP(Core.getLangFile().getConfig().getString("withdraw-exp")),
    WITHDRAW_MONEY(Core.getLangFile().getConfig().getString("withdraw-money")),
    REDEEM_EXP(Core.getLangFile().getConfig().getString("redeem-exp")),
    REDEEM_MONEY(Core.getLangFile().getConfig().getString("redeem-money")),

    MIN_MONEY_WITHDRAW(Core.getLangFile().getConfig().getString("min-withdraw-money")),
    MAX_MONEY_WITHDRAW(Core.getLangFile().getConfig().getString("max-withdraw-money")),
    MIN_EXP_WITHDRAW(Core.getLangFile().getConfig().getString("min-withdraw-exp")),
    MAX_EXP_WITHDRAW(Core.getLangFile().getConfig().getString("max-withdraw-exp")),

    ARMORBREAK_HELMET(Core.getLangFile().getConfig().getString("armor-break.helmet")),
    ARMORBREAK_CHESTPLATE(Core.getLangFile().getConfig().getString("armor-break.chestplate")),
    ARMORBREAK_LEGGINGS(Core.getLangFile().getConfig().getString("armor-break.leggings")),
    ARMORBREAK_BOOTS(Core.getLangFile().getConfig().getString("armor-break.boots")),

    STAFFMODE_ENTER(Core.getLangFile().getConfig().getString("staffmode.entered")),
    STAFFMODE_EXIT(Core.getLangFile().getConfig().getString("staffmode.exited")),

    CPSCHECK_ACTIVE(Core.getLangFile().getConfig().getString("cps-check.active")),
    CPSCHECK_RESULT(Core.getLangFile().getConfig().getString("cps-check.result")),

    HEAL_HEALED(Core.getLangFile().getConfig().getString("heal.healed")),
    HEAL_COOLDOWN(Core.getLangFile().getConfig().getString("heal.cooldown")),
    HEAL_CANNOT_HEAL_OTHERS(Core.getLangFile().getConfig().getString("heal.cannot-heal-others")),

    FEED_FED(Core.getLangFile().getConfig().getString("feed.fed")),
    FEED_COOLDOWN(Core.getLangFile().getConfig().getString("feed.cooldown")),
    FEED_CANNOT_FEED_OTHERS(Core.getLangFile().getConfig().getString("feed.cannot-feed-others")),

    VAULT_RENAME(Core.getLangFile().getConfig().getString("player-vault.rename")),
    VAULT_RENAMED(Core.getLangFile().getConfig().getString("player-vault.renamed")),
    VAULT_NAMING_CANCELLED(Core.getLangFile().getConfig().getString("player-vault.naming-cancel")),

    SPEED_POTION_TOGGLE(Core.getLangFile().getConfig().getString("potions-affects.speed")),
    JUMP_POTION_TOGGLE(Core.getLangFile().getConfig().getString("potions-affects.jump")),
    REGEN_POTION_TOGGLE(Core.getLangFile().getConfig().getString("potions-affects.regen")),
    ABSORPTION_POTION_TOGGLE(Core.getLangFile().getConfig().getString("potions-affects.absorption")),
    NIGHTVISION_POTION_TOGGLE(Core.getLangFile().getConfig().getString("potions-affects.nightvision")),


    VOUCHER_CREATE(Core.getLangFile().getConfig().getString("voucher.create")),
    VOUCHER_REMOVE(Core.getLangFile().getConfig().getString("voucher.remove")),
    VOUCHER_NONE(Core.getLangFile().getConfig().getString("voucher.no-vouchers")),
    VOUCHER_GIVE(Core.getLangFile().getConfig().getString("voucher.give")),
    VOUCHER_GIVEALL(Core.getLangFile().getConfig().getString("voucher.giveall")),
    VOUCHER_EXIST(Core.getLangFile().getConfig().getString("voucher.exist")),
    VOUCHER_INVALID(Core.getLangFile().getConfig().getString("voucher.invalid")),

    SELL_CHEST_SELL(Core.getLangFile().getConfig().getString("sellchest.sell")),
    SELL_CHEST_SOLD(Core.getLangFile().getConfig().getString("sellchest.sold")),

    COBWEB_LIMIT(Core.getLangFile().getConfig().getString("cobweb-limit")),

    COOLDOWN_GOLDEN_APPLE(Core.getLangFile().getConfig().getString("cooldowns.goldenapple")),
    COOLDOWN_GOD_APPLE(Core.getLangFile().getConfig().getString("cooldowns.godapple")),

    BOT_CHECK_FAIL(Core.getLangFile().getConfig().getString("botcheck.fail")),
    BOT_CHECK_COMPLETE(Core.getLangFile().getConfig().getString("botcheck.complete")),
    BOT_CHECK_ASK(Core.getLangFile().getConfig().getString("botcheck.ask")),
    BOT_CHECK_SENT(Core.getLangFile().getConfig().getString("botcheck.sent")),
    ;

    private String node;

    Language(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    private static boolean isSet(String node) {
        return Core.getLangFile().getConfig().contains(node);
    }

    private static void setNode(String node, String msg) {
        if (!isSet(node)) {
            Core.getLangFile().getConfig().set(node, msg);
        }
        Core.getLangFile().saveConfig();
    }

    public static void init() {
        setNode("no-perm", "&cYou do not have permission to use that command!");
        setNode("player-offline", "&cThat player could not be found or is not online.");
        setNode("nothing-in-hand", "&cYou do not have anything in your hand though o.O");
        setNode("players-only", "Sorry but that command is only available for players in-game.");
        setNode("item-blocked", "&cCrafting of that item is currently disabled!");
        setNode("not-a-number", "&cThat is not a valid number, try again.");
        setNode("ingot-convert", "&bConverted all of your Ingots to blocks!");
        setNode("chat.mute", "&cChat has been muted by &b{player}!");
        setNode("chat.unmute", "&aChat has be un-muted by &b{player}");
        setNode("freeze.freezer-freeze", "&3{player} &bhas been frozen.");
        setNode("freeze.freezer-unfreeze", "&3{player} &bhas been unfrozen");
        setNode("freeze.target-freeze", "&bYou have been frozen by &3{player}");
        setNode("freeze.target-unfreeze", "&bYou have been unfrozen by &3{player}");
        setNode("not-enough-money", "&cYou do not have enough money to withdraw.");
        setNode("not-enough-exp", "&cYou do not have enough exp to withdraw");
        setNode("withdraw-exp", "&c&l-{value} EXP");
        setNode("withdraw-money", "&c&l-${value}");
        setNode("redeem-exp", "&a&l+{value} EXP");
        setNode("redeem-money", "&a&l+${value}");
        setNode("armor-break.helmet", "&c&l(!) Helmet Broke (!)");
        setNode("armor-break.chestplate", "&c&l(!) Chestplate Broke (!)");
        setNode("armor-break.leggings", "&c&l(!) Leggings Broke (!)");
        setNode("armor-break.boots", "&c&l(!) Boots Broke (!)");
        setNode("min-withdraw-money", "&cPlease withdraw a minimum of $10");
        setNode("max-withdraw-money", "&cYou can only withdraw $1M at a time.");
        setNode("min-withdraw-exp", "&cPlease withdraw a minimum of 10EXP");
        setNode("max-withdraw-exp", "&cYou can only withdraw 25,000 EXP at a time.");
        setNode("staffmode.entered", "&a(!) Entered staff mode, have fun :D");
        setNode("staffmode.exited", "&c(!) Exited staff mode, good bye :D");
        setNode("cps-check.active", "&c(!) This user is currently being CPS tested!");
        setNode("cps-check.result", "&a(!) The player &6{player} &ahas &d{cps}&aCPS");
        setNode("heal.healed", "&a(!) You've successfully been healed.");
        setNode("heal.cooldown", "&a(!) You can use /heal in another &6{timeleft} &aseconds.");
        setNode("heal.cannot-heal-others", "&a(!) You lack the permissions to heal others.");
        setNode("feed.fed", "&a(!) You've successfully been fed.");
        setNode("feed.cooldown", "&a(!) You can use /feed in another &6{timeleft} &aseconds.");
        setNode("feed.cannot-feed-others", "&a(!) You lack the permissions to feed others.");
        setNode("player-vault.rename", "&aPlease enter a new name for your vault.\n&bUse the word 'cancel' to cancel.");
        setNode("player-vault.renamed", "&aYour vault #{vault_number} has been renamed to&f:{name}");
        setNode("player-vault.naming-cancel", "&c(!) You cancelled renaming your vault.");
        setNode("potions-affects.speed", "&c(!) You toggled the speed potion affect");
        setNode("potions-affects.nightvision", "&c(!) You toggled the night vision potion affect");
        setNode("potions-affects.regen", "&c(!) You toggled the regen potion affect");
        setNode("potions-affects.jump", "&c(!) You toggled the jump potion affect");
        setNode("potions-affects.absorption", "&c(!) You toggled the absorption potion affect");

        setNode("voucher.create", "&a(!) You created a new voucher named&f: &6{name}");
        setNode("voucher.remove", "&c(!) You removed the voucher named&f: &6{name}");
        setNode("voucher.give", "&aYou gave &6{player} x{amount} &b{name} &aVouchers");
        setNode("voucher.giveall", "&aYou gave everyone x{amount} &b{name} &aVouchers");
        setNode("voucher.no-vouchers", "&C(!) There are no vouchers available at the moment.");
        setNode("voucher.exist", "&C(!) There is already a voucher with the id of&f: &6{name}");
        setNode("voucher.invalid", "&C(!) There is no voucher with the id of&f: &6{name}");

        setNode("sellchest.sold", "&b(!) Sold all contents of the chest for &a${price}");
        setNode("sellchest.sell", "&b(!) Left-Click the chest to sell it's contents.");

        setNode("cobweb-limit", "&c(!) Cannot place more than {amount} cobwebs on the same y axis.");

        setNode("cooldowns.goldenapple", "&c(!) {time} seconds left before you can eat other golden apple.");
        setNode("cooldowns.godapple", "&c(!) {time} seconds left before you can eat other god apple.");

        setNode("botcheck.fail", "&c(!) {player} &bhas failed the bot/anti-mobaura captcha.");
        setNode("botcheck.complete", "&b(!) Congratulations, you've successfully completed it.");
        setNode("botcheck.ask", "&c(!) Please complete this captcha to continue.");
        setNode("botcheck.sent", "&b(!) Sent a captcha test to &b{player}");
    }
}
