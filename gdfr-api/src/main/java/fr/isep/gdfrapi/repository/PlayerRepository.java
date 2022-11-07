package fr.isep.gdfrapi.repository;

import fr.isep.gdfrapi.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String> {

    @Query(value = "SELECT * FROM players WHERE username LIKE %:username%", nativeQuery = true)
    Iterable<Player> findByNameResearch(@Param("username") String username);

    @Query(value = "SELECT * FROM players WHERE id_gd = :id", nativeQuery = true)
    Iterable<Player> findByGDid(@Param("id") Integer idGD);

    @Query(value = "SELECT * FROM players WHERE id_dl = :id", nativeQuery = true)
    Iterable<Player> findByDLid(@Param("id") Integer idDL);

    @Query(value = "SELECT * FROM players " +
            "LEFT JOIN statsgd ON players.id_gd = statsgd.id_gd " +
            "LEFT JOIN statsdl ON players.id_dl = statsdl.id_dl " +
            "WHERE players.username = :username", nativeQuery = true)
    Optional<Player> findAllPlayerData(@Param("username") String username);
}
