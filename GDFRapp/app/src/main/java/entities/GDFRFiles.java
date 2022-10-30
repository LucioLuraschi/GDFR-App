package entities;

import servers.BDDServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GDFRFiles {

    private List<Player> players;
    private BDDServer bdd;

    private File playersFile;
    private File starsFile;
    private File diamondsFile;
    private File userCoinsFile;
    private File demonsFile;
    private File cpsFile;
    private File demonListFile;

    private String[] orderStartWithChars = {
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    };

    public GDFRFiles() throws IOException, SQLException, ClassNotFoundException {
        this.playersFile = new File("src/main/java/files/GDFR_players.csv");
        this.starsFile = new File("src/main/java/files/GDFR_stars.csv");
        this.diamondsFile = new File("src/main/java/files/GDFR_diamonds.csv");
        this. userCoinsFile = new File("src/main/java/files/GDFR_user_coins.csv");
        this.demonsFile = new File("src/main/java/files/GDFR_demons.csv");
        this.cpsFile = new File("src/main/java/files/GDFR_CPs.csv");
        this.demonListFile = new File("src/main/java/files/GDFR_demonList.csv");

        this.playersFile.createNewFile();
        this.starsFile.createNewFile();
        this.diamondsFile.createNewFile();
        this.userCoinsFile.createNewFile();
        this.demonsFile.createNewFile();
        this.cpsFile.createNewFile();
        this.demonListFile.createNewFile();

        this.bdd = new BDDServer();
        this.players = this.bdd.getAllPlayers();
    }

    public File[] getAllFiles() {
        File[] files = {
            this.playersFile,
            this.starsFile,
            this.diamondsFile,
            this.userCoinsFile,
            this.demonsFile,
            this.cpsFile,
            this.demonListFile
        };
        return files;
    }

    public GDFRFiles writeAllFiles() throws IOException {
        writePlayersFile();
        writeStarsFile();
        writeDiamondsFile();
        writeUserCoinsFile();
        writeDemonsFile();
        writeCPsFile();
        writeDLFile();
        return this;
    }

    private void writePlayersFile() throws IOException {
        FileWriter writer = new FileWriter(playersFile);
        writer.write(";;;;STARS;;;;\n");
        writer.write("\n");
        for (String starter : this.orderStartWithChars) {
            List<Player> playersStarterCharList = this.players.stream()
                    .filter((player -> player.getUsername().toLowerCase().startsWith(starter)))
                    .collect(Collectors.toList());
            String title = "LETTRE " + starter.toUpperCase();
            writer.write(";;;;" + title + ";;;;\n");
            writer.write(";;;Pseudo;Stars;Diamonds;Secret Coins;User Coins;Demons;CPs\n");
            for (Player player: playersStarterCharList) {
                writer.write(player.getPlayerDataCSVLine() + "\n");
            }
            writer.write(";;;;;;;;\n");

        }
        writer.write(";;;;Autres;;;;\n");
        writer.write(";;;Pseudo;Stars;Diamonds;Secret Coins;User Coins;Demons;CPs\n");
        List<Player> playersStarterCharList = players.stream()
                .filter(player -> Pattern.compile("^[0-9]", Pattern.CASE_INSENSITIVE)
                        .matcher(player.getUsername()).find())
                .collect(Collectors.toList());
        for (Player player: playersStarterCharList) {
            writer.write(player.getPlayerDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeStarsFile() throws IOException {
        FileWriter writer = new FileWriter(starsFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdGD() != 0 && player.getRank() != 0)
                .sorted(Comparator.comparingInt(Player::getStars).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;STARS;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Nombre de stars\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getStarsDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeDiamondsFile() throws IOException {
        FileWriter writer = new FileWriter(diamondsFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdGD() != 0 && player.getRank() != 0)
                .sorted(Comparator.comparingInt(Player::getDiamonds).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;DIAMANDS;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Nombre de diamands\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getDiamondsDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeUserCoinsFile() throws IOException {
        FileWriter writer = new FileWriter(userCoinsFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdGD() != 0 && player.getRank() != 0)
                .sorted(Comparator.comparingInt(Player::getUserCoins).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;USER COINS;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Nombre de user coins\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getUserCoinsDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeDemonsFile() throws IOException {
        FileWriter writer = new FileWriter(demonsFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdGD() != 0 && player.getRank() != 0)
                .sorted(Comparator.comparingInt(Player::getDemons).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;DEMONS;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Nombre de demons\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getDemonsDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeCPsFile() throws IOException {
        FileWriter writer = new FileWriter(cpsFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdGD() != 0 && player.getRank() != 0 && player.getCps() != 0)
                .sorted(Comparator.comparingInt(Player::getCps).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;CPs;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Nombre de CPs\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getCPsDataCSVLine() + "\n");
        }
        writer.close();
    }

    private void writeDLFile() throws IOException {
        FileWriter writer = new FileWriter(demonListFile);
        List<Player> starsLeaderboardList = this.players.stream()
                .filter(player -> player.getIdDL() != 0 && player.getDemonListScore() != 0)
                .sorted(Comparator.comparingInt(Player::getDemonListScore).reversed())
                .collect(Collectors.toList());
        writer.write(";;;;DEMONLIST STATS;;;;\n");
        writer.write("\n");
        writer.write(";;;Rank;Pseudo;Points DL;Hardest\n");
        for (int i = 0; i < starsLeaderboardList.size(); i++) {
            Player player = starsLeaderboardList.get(i);
            writer.write(";;;" + (i+1) + ";" + player.getDLDataCSVLine() + "\n");
        }
        writer.close();
    }
}
