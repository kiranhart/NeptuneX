package com.shadebyte.neptunex.utils.faction;

import com.massivecraft.factions.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionUUID implements FactionUtil {

    @Override
    public boolean playerIsInFaction(Player p) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        return fp.hasFaction();
    }

    @Override
    public boolean isInWarzone(Player p) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        Faction fac = Board.getInstance().getFactionAt(fp.getLastStoodAt());
        return fac.isWarZone();
    }

    @Override
    public boolean playerIsLeader(Player p) {
        if (!playerIsInFaction(p)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean insideOwnClaim(Player p) {
        if (!playerIsInFaction(p)) {
            return false;
        }

        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        return fp.isInOwnTerritory();
    }

    @Override
    public String getFactionName(Player p) {
        if (!playerIsInFaction(p)) {
            return null;
        }

        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        return fp.getFactionId();
    }

    @Override
    public List<Player> getCurrentMembers(Player p) {
        if (!playerIsInFaction(p)) {
            return null;
        }

        List<Player> players = new ArrayList<>();
        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        Faction faction = Factions.getInstance().getByTag(fp.getFactionId());
        for (FPlayer fPlayer : faction.getFPlayers()) {
            players.add(fPlayer.getPlayer());
        }

        return players;
    }

    @Override
    public int getTotalMembers(Player p) {
        if (!playerIsInFaction(p)) {
            return 0;
        }

        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        return fp.getFaction().getFPlayers().size();
    }

    @Override
    public int getTotalOnlineMembers(Player p) {
        if (!playerIsInFaction(p)) {
            return 0;
        }

        int total = 0;

        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        Faction faction = Factions.getInstance().getByTag(fp.getFactionId());
        for (FPlayer fPlayer : faction.getFPlayers()) {
            if (fPlayer.isOnline()) {
                total++;
            }
        }
        return total;
    }

    @Override
    public int getTotalOfflineMembers(Player p) {
        if (!playerIsInFaction(p)) {
            return 0;
        }

        int total = 0;

        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        Faction faction = Factions.getInstance().getByTag(fp.getFactionId());
        for (FPlayer fPlayer : faction.getFPlayers()) {
            if (fPlayer.isOffline()) {
                total++;
            }
        }
        return total;
    }
}
