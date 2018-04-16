package com.shadebyte.neptunex.utils.faction;

import org.bukkit.entity.Player;

import java.util.List;

public interface FactionUtil {

    boolean playerIsInFaction(Player p);

    boolean isInWarzone(Player p);

    boolean playerIsLeader(Player p);

    boolean insideOwnClaim(Player p);

    String getFactionName(Player p);

    List<Player> getCurrentMembers(Player p);

    int getTotalMembers(Player p);

    int getTotalOnlineMembers(Player p);

    int getTotalOfflineMembers(Player p);
}
