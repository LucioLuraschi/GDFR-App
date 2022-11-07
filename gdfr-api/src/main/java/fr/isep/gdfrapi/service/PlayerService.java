package fr.isep.gdfrapi.service;

import fr.isep.gdfrapi.model.Player;
import fr.isep.gdfrapi.repository.PlayerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Player> getPlayer(final String username) {
        return playerRepository.findAllPlayerData(username);
    }

    public Iterable<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public void deletePlayer(final String username) {
        playerRepository.deleteById(username);
    }

    public Player savePlayer(String username) {
        Player player = new Player();
        player.setUsername(username);

        Player savedPlayer = playerRepository.save(player);
        return savedPlayer;
    }

    public Iterable<Player> searchPlayer(String username) {
        Iterable<Player> players = playerRepository.findByNameResearch(username);
        return players;
    }
}
