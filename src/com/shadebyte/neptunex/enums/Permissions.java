package com.shadebyte.neptunex.enums;

public enum Permissions {

    CRAFTING_BYPASS("NeptuneX.misc.craftingbypass"),
    STAFF_PERM("NeptuneX.misc.staffperm"),

    BLOCK_COMMAND("NeptuneX.cmd.block"),
    PLAYERVAULT_COMMAND("NeptuneX.cmd.playervault"),

    GENBUCKET_COMMAND("NeptuneX.cmd.genbucket"),

    HEAL_COMMAND("NeptuneX.cmd.heal"),
    FEED_COMMAND("NeptuneX.cmd.feed"),

    SPEED_AFFECT_COMMAND("NeptuneX.cmd.speed"),
    JUMP_AFFECT_COMMAND("NeptuneX.cmd.jumpboost"),
    NIGHTVISION_AFFECT_COMMAND("NeptuneX.cmd.nightvision"),
    REGEN_AFFECT_COMMAND("NeptuneX.cmd.regen"),
    ABSORPTION_AFFECT_COMMAND("NeptuneX.cmd.absorption"),

    VOUCHER_COMMAND("NeptuneX.cmd.voucher"),
    VOUCHER_CREATE_COMMAND("NeptuneX.cmd.voucher.create"),
    VOUCHER_REMOVE_COMMAND("NeptuneX.cmd.voucher.remove"),
    VOUCHER_GIVE_COMMAND("NeptuneX.cmd.voucher.give"),
    VOUCHER_GIVEALL_COMMAND("NeptuneX.cmd.voucher.giveall"),
    VOUCHER_LISTCOMMAND("NeptuneX.cmd.voucher.list"),

    ORES_COMMAND("NeptuneX.cmd.ores"),
    CPS_COMMAND("NeptuneX.cmd.cps"),
    WITHDRAW_COMMAND("NeptuneX.cmd.withdraw"),
    XPBOTTLE_COMMAND("NeptuneX.cmd.xpbottle"),
    STAFF_MODE_COMMAND("NeptuneX.cmd.staffmode"),

    SELL_CHEST_COMMAND("NeptuneX.cmd.sellchest"),

    FREEZE_COMMAND("NeptuneX.cmd.freeze");

    private String node;

    Permissions(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }
}
