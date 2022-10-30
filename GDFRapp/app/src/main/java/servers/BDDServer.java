package servers;

import entities.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDDServer {

    private final String USER = "Atlas";
    private final String PASS = "GeometryDashFR2021!";
    private final String URLBDD = "jdbc:mysql://localhost:3306/gdfr";
    private final Connection bdd;

    public BDDServer() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.bdd = DriverManager.getConnection(URLBDD, USER, PASS);
    }

    // Functions that make checks in the database //
    public boolean isPlayerGDInBDD(@NotNull Player player) throws SQLException {
        PreparedStatement foundPlayerStatement = this.bdd.prepareStatement(
                "SELECT id_gd FROM players WHERE id_gd = ? OR username = ?");
        foundPlayerStatement.setInt(1, player.getIdGD());
        foundPlayerStatement.setString(2, player.getUsername());
        ResultSet foundPlayer = foundPlayerStatement.executeQuery();
        return foundPlayer.next();
    }

    // Functions that add data on the database //
    public int addPlayerWithoutData(@NotNull Player player) throws SQLException {
        PreparedStatement addPlayerStatement = this.bdd.prepareStatement(
                "INSERT INTO players (username) VALUES (?)");
        addPlayerStatement.setString(1, player.getUsername());
        int nbChanges = addPlayerStatement.executeUpdate();
        return nbChanges;
    }

    public int addPlayerWithGDid(@NotNull Player player) throws  SQLException {
        PreparedStatement addPlayerStatement = this.bdd.prepareStatement(
                "INSERT INTO players (username, id_gd) VALUES (?,?)");
        addPlayerStatement.setString(1, player.getUsername());
        addPlayerStatement.setInt(2, player.getIdGD());
        int nbChanges = addPlayerStatement.executeUpdate();
        return nbChanges;
    }

    public int addGDIDToPlayerData(@NotNull Player player) throws SQLException {
        PreparedStatement addGDIDStatement = this.bdd.prepareStatement(
                "UPDATE players SET id_gd = ? WHERE username = ?");
        addGDIDStatement.setString(2, player.getUsername());
        addGDIDStatement.setInt(1, player.getIdGD());
        int nbChanges = addGDIDStatement.executeUpdate();
        return nbChanges;
    }

    public int addDLIDToPlayerData(@NotNull Player player) throws SQLException {
        PreparedStatement addDLIDStatement = this.bdd.prepareStatement(
                "UPDATE players SET id_dl = ? WHERE username = ?");
        addDLIDStatement.setString(2, player.getUsername());
        addDLIDStatement.setInt(1, player.getIdDL());
        int nbChanges = addDLIDStatement.executeUpdate();
        return nbChanges;
    }

    public int addGDData(@NotNull Player player) throws SQLException {
        PreparedStatement addGDStatsStatement = this.bdd.prepareStatement(
                "INSERT INTO statsgd (id_gd, stars, diamonds, secret_coins, user_coins, demons, cps, rank)" +
                        "VALUES (?,?,?,?,?,?,?,?)");
        addGDStatsStatement.setInt(1, player.getIdGD());
        addGDStatsStatement.setInt(2, player.getStars());
        addGDStatsStatement.setInt(3, player.getDiamonds());
        addGDStatsStatement.setInt(4, player.getSecretCoins());
        addGDStatsStatement.setInt(5, player.getUserCoins());
        addGDStatsStatement.setInt(6, player.getDemons());
        addGDStatsStatement.setInt(7, player.getCps());
        addGDStatsStatement.setInt(8, player.getRank());
        int nbChanges = addGDStatsStatement.executeUpdate();
        return nbChanges;
    }

    public int addDLData(@NotNull Player player) throws SQLException {
        PreparedStatement addDLStatsStatement = this.bdd.prepareStatement(
                "INSERT INTO statsdl (id_dl, points, hardest) VALUES (?,?,?)");
        addDLStatsStatement.setInt(1, player.getIdDL());
        addDLStatsStatement.setInt(2, player.getDemonListScore());
        addDLStatsStatement.setString(3, player.getHardestDemonList());
        int nbChanges = addDLStatsStatement.executeUpdate();
        return nbChanges;
    }

    // Functions that update the data
    public int updatePlayerDataFromGDData(@NotNull Player player) throws SQLException {
        PreparedStatement updatePlayerNameFromGDStatement = this.bdd.prepareStatement(
                "UPDATE players SET username = ? WHERE id_gd = ?");
        updatePlayerNameFromGDStatement.setString(1, player.getUsername());
        updatePlayerNameFromGDStatement.setInt(2, player.getIdGD());
        int nbChanges = updatePlayerNameFromGDStatement.executeUpdate();
        return nbChanges;
    }

    public int updatePlayerDataFromDLData(@NotNull Player player) throws SQLException {
        PreparedStatement updatePlayerNameFromDLStatement = this.bdd.prepareStatement(
                "UPDATE players SET username = ? WHERE id_dl = ?");
        updatePlayerNameFromDLStatement.setString(1, player.getUsername());
        updatePlayerNameFromDLStatement.setInt(2, player.getIdDL());
        int nbChanges = updatePlayerNameFromDLStatement.executeUpdate();
        return nbChanges;
    }

    public int updateGDData(@NotNull Player player) throws SQLException {
        PreparedStatement updateGDStatsStatement = this.bdd.prepareStatement(
                "UPDATE statsgd SET stars = ?, " +
                        "diamonds = ?, " +
                        "secret_coins = ?, " +
                        "user_coins = ?, " +
                        "demons = ?, " +
                        "cps = ?, " +
                        "rank = ? " +
                        "WHERE id_gd = ?");
        updateGDStatsStatement.setInt(1, player.getStars());
        updateGDStatsStatement.setInt(2, player.getDiamonds());
        updateGDStatsStatement.setInt(3, player.getSecretCoins());
        updateGDStatsStatement.setInt(4, player.getUserCoins());
        updateGDStatsStatement.setInt(5, player.getDemons());
        updateGDStatsStatement.setInt(6, player.getCps());
        updateGDStatsStatement.setInt(7, player.getRank());
        updateGDStatsStatement.setString(8, Integer.toString(player.getIdGD()));
        int nbChanges = updateGDStatsStatement.executeUpdate();
        return nbChanges;
    }

    public int updateDLData(@NotNull Player player) throws SQLException {
        PreparedStatement updateDLStatsStatement = this.bdd.prepareStatement(
                "UPDATE statsdl SET points = ?, hardest = ? WHERE id_dl = ?");
        updateDLStatsStatement.setInt(1, player.getDemonListScore());
        updateDLStatsStatement.setString(2, player.getHardestDemonList());
        updateDLStatsStatement.setInt(3, player.getIdDL());
        int nbChanges = updateDLStatsStatement.executeUpdate();
        return nbChanges;
    }

    // Functions that select the data in the BDD
    public List<Player> getGDidList() throws SQLException {
        List<Player> idGDList = new ArrayList<Player>();
        ResultSet GDidListResult = this.bdd.createStatement().executeQuery(
                "SELECT username, id_gd FROM players WHERE id_gd IS NOT NULL");
        while (GDidListResult.next()) {
            int idGD = GDidListResult.getInt("id_gd");
            String username = GDidListResult.getString("username");
            Player player = new Player(username);
            player.setIdGD(idGD);
            idGDList.add(player);
        }
        return idGDList;
    }

    public List<Player> getDLidList() throws SQLException {
        List<Player> idDLList = new ArrayList<Player>();
        ResultSet GDidListResult = this.bdd.createStatement().executeQuery(
                "SELECT username, id_dl FROM players WHERE id_dl IS NOT NULL");
        while (GDidListResult.next()) {
            int idDL = GDidListResult.getInt("id_dl");
            String username = GDidListResult.getString("username");
            Player player = new Player(username);
            player.setIdDL(idDL);
            idDLList.add(player);
        }
        return idDLList;
    }

    public List<Player> getPlayerListWithData() throws SQLException {
        List<Player> playersList = new ArrayList<Player>();
        ResultSet namesListResult = this.bdd.createStatement().executeQuery(
                "SELECT username, id_gd, id_dl FROM players WHERE id_gd IS NOT NULL OR id_dl IS NOT NULL"
        );
        while (namesListResult.next()) {
            String name = namesListResult.getString("username");
            int idGD = namesListResult.getInt("id_gd");
            int idDL = namesListResult.getInt("id_dl");
            Player player = new Player(name);
            player.setIdGD(idGD); player.setIdDL(idDL);
            player = getPlayerData(player);
            playersList.add(player);
        }
        return playersList;
    }

    public List<Player> getAllPlayers() throws SQLException {
        List<Player> playersList = new ArrayList<Player>();
        ResultSet namesListResult = this.bdd.createStatement().executeQuery(
                "SELECT username FROM players ORDER BY username"
        );
        while (namesListResult.next()) {
            String name = namesListResult.getString("username");
            Player player = new Player(name);
            player = getPlayerData(player);
            playersList.add(player);
        }
        return playersList;
    }

    public List<Player> getPlayerListWithoutData() throws SQLException {
        List<Player> playersList = new ArrayList<Player>();
        ResultSet namesListResult = this.bdd.createStatement().executeQuery(
                "SELECT username FROM players WHERE id_gd IS NULL AND id_dl IS NULL"
        );
        while (namesListResult.next()) {
            String name = namesListResult.getString("username");
            Player player = new Player(name);
            playersList.add(player);
        }
        return playersList;
    }

    public Player getPlayerData(@NotNull Player player) throws SQLException {
        PreparedStatement playerDataStatement = this.bdd.prepareStatement(
                "SELECT * FROM players " +
                        "LEFT JOIN statsgd ON players.id_gd = statsgd.id_gd " +
                        "LEFT JOIN statsdl ON players.id_dl = statsdl.id_dl " +
                        "WHERE players.username = ? OR players.id_gd = ?"
        );
        playerDataStatement.setString(1, player.getUsername());
        playerDataStatement.setInt(2, player.getIdGD());
        ResultSet playerDataInBDD = playerDataStatement.executeQuery();
        if (playerDataInBDD.next()) {
            player.setIdGD(playerDataInBDD.getInt("players.id_gd"));
            player.setIdDL(playerDataInBDD.getInt("players.id_dl"));
            player.setUsername(playerDataInBDD.getString("players.username"));
            if (playerDataInBDD.getInt("players.id_gd") != 0)  {
                player.setIdGD(playerDataInBDD.getInt("players.id_gd"));
                player.setGDStats(
                        playerDataInBDD.getInt("statsgd.stars"),
                        playerDataInBDD.getInt("statsgd.diamonds"),
                        playerDataInBDD.getInt("statsgd.secret_coins"),
                        playerDataInBDD.getInt("statsgd.user_coins"),
                        playerDataInBDD.getInt("statsgd.demons"),
                        playerDataInBDD.getInt("statsgd.cps"),
                        playerDataInBDD.getInt("statsgd.rank")
                );
            }
            if (playerDataInBDD.getInt("players.id_dl") != 0) {
                player.setIdDL(playerDataInBDD.getInt("players.id_dl"));
                player.setDemonListStats(
                        playerDataInBDD.getString("players.username"),
                        playerDataInBDD.getString("statsdl.hardest"),
                        playerDataInBDD.getInt("statsdl.points")
                );
            }
        }
        return player;
    }

    // Function that close the database
    public void bddLogOut() throws SQLException {
        this.bdd.close();
        System.out.println("La bdd a été fermée");
    }

}
