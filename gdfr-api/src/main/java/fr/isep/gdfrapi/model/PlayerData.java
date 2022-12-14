package fr.isep.gdfrapi.model;

import jdash.common.entity.GDUserProfile;
import org.jetbrains.annotations.NotNull;


public class PlayerData {

    private String username;
    private int idGD;
    private int idDL;
    private int stars;
    private int diamonds;
    private int secretCoins;
    private int userCoins;
    private int demons;
    private int cps;
    private int rank;
    private String hardestDemonList;
    private int demonListScore;

    public PlayerData(String username) {
        this.username = username;
        this.demonListScore = 0;
        this.hardestDemonList = "";
        this.idGD = 0;
        this.idDL = 0;
    }

    // Setters


    public void setUsername(String username) {
        this.username = username;
    }

    public void setDemonListStats(String username, String hardest, int demonListScore) {
        this.hardestDemonList = hardest;
        this.demonListScore = demonListScore;
        this.username = username;
    }

    public void setGDStats(@NotNull GDUserProfile user) {
        this.username = user.name();
        this.idGD = (int) user.accountId();
        this.stars = user.stars();
        this.diamonds = user.diamonds();
        this.secretCoins = user.secretCoins();
        this.userCoins = user.userCoins();
        this.demons = user.demons();
        this.cps = user.creatorPoints();
        this.rank = user.globalRank();
    }

    public void setGDStats(int stars, int diamonds, int secretCoins, int userCoins, int demons, int cps, int rank) {
        this.stars = stars;
        this.diamonds = diamonds;
        this.secretCoins = secretCoins;
        this.userCoins = userCoins;
        this.demons = demons;
        this.cps = cps;
        this.rank = rank;
    }

    public void setIdGD(int idGD) {
        this.idGD = idGD;
    }

    public void setIdDL(int idDL) {
        this.idDL = idDL;
    }

    @Override
    public String toString() {
        String jsonPlayer = "";
        jsonPlayer += "{";
        jsonPlayer += "\"username\" : \"" + this.username + "\",";
        jsonPlayer += "\"id_gd\" : \"" + this.idGD + "\",";
        jsonPlayer += "\"id_dl\" : \"" + this.idDL + "\"";
        if (idGD != 0) {
            jsonPlayer += ",\"stars\" : " + this.stars + ",";
            jsonPlayer += "\"diamonds\" : " + this.diamonds + ",";
            jsonPlayer += "\"secret_coins\" : " + this.secretCoins + ",";
            jsonPlayer += "\"user_coins\" : " + this.userCoins + ",";
            jsonPlayer += "\"demons\" : \"" + this.demons + "\",";
            jsonPlayer += "\"rank\" : " + this.rank + "";
        }
        if (idDL != 0) {
            jsonPlayer += ",\"dl_points\" : \"" + this.demonListScore + "\",";
            jsonPlayer += "\"hardest\" : \"" + this.hardestDemonList + "\"";
        }
        jsonPlayer += "}";
        return jsonPlayer;
    }

    // Getters //

    public String getUsername() {
        return username;
    }

    public int getIdGD() {
        return idGD;
    }

    public int getIdDL() {
        return idDL;
    }

    public String getHardestDemonList() {
        return hardestDemonList;
    }

    public int getUserCoins() {
        return userCoins;
    }

    public int getRank() {
        return rank;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public int getDemons() {
        return demons;
    }

    public int getCps() {
        return cps;
    }

    public int getDemonListScore() {
        return demonListScore;
    }

    public int getSecretCoins() {
        return secretCoins;
    }

    public int getStars() {
        return stars;
    }

    // Lines for the .csv files
    public String getPlayerDataCSVLine() {
        if (idGD != 0) {
            return ";;;" + username + ";" + stars + ";" + diamonds + ";" + secretCoins + ";"
                    + userCoins + ";" + demons + ";" + cps + ";" + ((rank ==0) ? "Pas de rank" : "");
        }
        return ";;;" + username + ";???;???;???;???;???;???;";
    }

    public String getStarsDataCSVLine() {
        return username + ";" + stars + ";";
    }

    public String getDiamondsDataCSVLine() {
        return username + ";" + diamonds + ";";
    }

    public String getUserCoinsDataCSVLine() {
        return username + ";" + userCoins + ";";
    }

    public String getDemonsDataCSVLine() {
        return username + ";" + demons + ";";
    }

    public String getCPsDataCSVLine() {
        return username + ";" + cps + ";";
    }

    public String getDLDataCSVLine() {
        return username + ";" + (((double) demonListScore) / 100) + ";" + hardestDemonList;
    }
}
