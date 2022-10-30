package servers;

import entities.Player;
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

    public Player searchPlayerFromID(@NotNull Player player) {
        GDUserProfile userStats = this.gdServer.getUserProfile(player.getIdGD()).block();
        if (userStats != null) {
            player.setGDStats(userStats);
        }
        return player;
    }
}
