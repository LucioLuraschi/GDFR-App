package fr.isep.gdfrapi.service;

import fr.isep.gdfrapi.model.Player;
import fr.isep.gdfrapi.model.PlayerData;
import jdash.client.GDClient;
import jdash.common.entity.GDUserProfile;
import jdash.common.entity.GDUserStats;
import org.jetbrains.annotations.NotNull;

public class GDServer {

    private GDClient gdServer;

    public GDServer() {
        this.gdServer = GDClient.create();
    }

    public Player searchPlayerFromName(@NotNull Player player) {
        try {
            GDUserStats userSearch = this.gdServer.searchUsers(player.getUsername(), 0).blockFirst();
            if (userSearch != null) {
                GDUserProfile userStats = this.gdServer.getUserProfile(userSearch.accountId()).block();
                player.setGDStats(userStats);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            return player;
        }
    }

    public PlayerData searchPlayerFromID(@NotNull PlayerData player) {
        GDUserProfile userStats = this.gdServer.getUserProfile(player.getIdGD()).block();
        if (userStats != null) {
            player.setGDStats(userStats);
        }
        return player;
    }
}
