package servers;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import entities.Player;

public class APIServer {

    public String adminConnection(String name, String password) {
        String token = "";
        return token;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<Player>();
        return players;
    }

    public Player getPalyerData(@NonNull String playerName) {
        Player player = new Player(playerName);
        return player;
    }

    public void updatePlayer(@NonNull Player player) {

    }

    public boolean isPlayerInBDD(@NonNull Player player) {
        boolean isInBDD = false;
        return isInBDD;
    }
}
