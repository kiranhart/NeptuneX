package com.shadebyte.neptunex.api.packet;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NTitle {

    private static NTitle instance;

    private NTitle() {
    }

    public static NTitle getInstance() {
        if (instance == null) {
            instance = new NTitle();
        }
        return instance;
    }

    public void sendTitle(Player player, String msg, int fadeIn, int stay, int fadeOut) {
        try {

            Object enumTitle = NPacket.getInstance().getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object chat = NPacket.getInstance().getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + msg + "\"}");
            Constructor<?> titleConstructor = NPacket.getInstance().getNMSClass("PacketPlayOutTitle").getConstructor(NPacket.getInstance().getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NPacket.getInstance().getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(enumTitle, chat, fadeIn, stay, fadeOut);
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            playerConnection.getClass().getMethod("sendPacket", NPacket.getInstance().getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
