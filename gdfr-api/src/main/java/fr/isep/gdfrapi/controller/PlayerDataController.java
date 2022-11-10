package fr.isep.gdfrapi.controller;

import fr.isep.gdfrapi.model.Player;
import fr.isep.gdfrapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PlayerDataController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public Iterable<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/players/{username}")
    public Optional<Player> getPlayerWithId(@PathVariable String username) {
        return playerService.getPlayer(username);
    }

    @PostMapping("/players/{username}")
    public String addPlayer(@PathVariable String username) {
        return playerService.savePlayer(username);
    }

    @GetMapping("/players/search/{username}")
    public Iterable<Player> searchPlayerFromName(@PathVariable String username) {
        return playerService.searchPlayer(username);
    }
}
