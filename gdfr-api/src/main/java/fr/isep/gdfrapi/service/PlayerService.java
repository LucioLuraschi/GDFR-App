package fr.isep.gdfrapi.service;

import fr.isep.gdfrapi.model.Player;
import fr.isep.gdfrapi.repository.PlayerRepository;
import fr.isep.gdfrapi.repository.StatsGDRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StatsGDRepository statsGDRepository;

    public Optional<Player> getPlayer(final String username) {
        return playerRepository.findAllPlayerData(username);
    }

    public Iterable<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public void deletePlayer(final String username) {
        playerRepository.deleteById(username);
    }

    public String savePlayer(String username) {
        Optional<Player> playerOptional = playerRepository.findAllPlayerData(username);
        if (getPlayer(username).isPresent()) {
            Player player = playerOptional.get();
            return "{\"wasPlayerInDatabase\" : true, " +
                    "\"username\" : \"" + player.getUsername() + "\"," +
                    "\"isPlayerSaved\" : true, \"isGDDataSaved\" : "
                    + ((player.getIdGD() != null) ? "true" : "false") + "}";
        }
        Player player = new Player();
        player.setUsername(username);
        player = new GDServer().searchPlayerFromName(player);
        Player savedPlayer = playerRepository.save(player);
        if (savedPlayer != null) {
            if (player.getIdGD() != null) {
                statsGDRepository.save(player.getStatsGD());
                return "{\"wasPlayerInDatabase\" : false, " +
                        "\"username\" : \"" + player.getUsername() + "\"," +
                        "\"isPlayerSaved\" : true, " +
                        "\"isGDDataSaved\" : true}";
            }
            return "{\"wasPlayerInDatabase\" : false, \"isPlayerSaved\" : true, \"isGDDataSaved\" : false}";
        }
        return "{\"wasPlayerInDatabase\" : false, \"isPlayerSaved\" : false, \"isGDDataSaved\" : false}";
    }

    public Iterable<Player> searchPlayer(String username) {
        Iterable<Player> players = playerRepository.findByNameResearch(username);
        return players;
    }
}
