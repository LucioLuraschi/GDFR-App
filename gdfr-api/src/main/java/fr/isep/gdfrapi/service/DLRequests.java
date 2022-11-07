package fr.isep.gdfrapi.service;

import fr.isep.gdfrapi.model.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DLRequests {

    private int idDL = 0;
    private double DLpoints = 0.0;
    private String hardest = "";
    private long[] top75requirements = new long[75];

    private String username;

    public DLRequests(int idDL) {
        // Taking the ID of the player
        this.idDL = idDL;

        // Collecting the top 75 and their requirements
        setRequirementTop75();

        // Collecting the demons completed by the player in the list
        setPlayerData();

        // Rounding the score
        this.DLpoints = Math.ceil(this.DLpoints * 100);
    }

    public PlayerData insertDLData(@NotNull PlayerData player) {

        player.setDemonListStats(this.username, this.hardest, (int) this.DLpoints);
        return player;
    }

    private void setRequirementTop75() {
        // Making the request to the API
        try {
            URL urlDLTop75Request = new URL("https://pointercrate.com/api/v2/demons/listed/?limit=75");
            HttpURLConnection DLAPITop75conn = (HttpURLConnection) urlDLTop75Request.openConnection();
            DLAPITop75conn.setRequestMethod("GET");
            DLAPITop75conn.setRequestProperty("Accept", "application/json");
            StringBuilder response = new StringBuilder();
            String line;
            InputStream errorStream = DLAPITop75conn.getErrorStream();
            if (errorStream == null) {
                InputStream APIstream = DLAPITop75conn.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(APIstream);
                BufferedReader reader = new BufferedReader(streamReader);
                while ((line = reader.readLine()) != null) {
                    response.append(line + "\n");
                }
                reader.close();
                String dataTop75Result = response.toString();
                // Making the tab of requierement for the top 75
                JSONArray top75Data = (JSONArray) JSONValue.parse(dataTop75Result);
                for (int i = 0; i < top75Data.size(); i++) {
                    JSONObject dataDemon = (JSONObject) top75Data.get(i);
                    this.top75requirements[i] = (long) dataDemon.get("requirement");
                }

                this.DLpoints = Math.round(this.DLpoints * 100) / 100;
            } else {
                try (Scanner scanner = new Scanner(errorStream)) {
                    scanner.useDelimiter("\\Z");
                    String responseErr = scanner.next();
                    System.out.println(responseErr);
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private void setPlayerData() {
        try {
            // Making the request to the API
            URL urlDLRequest = new URL("https://pointercrate.com/api/v1/players/" + this.idDL + "/");
            HttpURLConnection DLAPIconn = (HttpURLConnection) urlDLRequest.openConnection();
            DLAPIconn.setRequestMethod("GET");
            DLAPIconn.setRequestProperty("Content", "application/json");
            StringBuilder response = new StringBuilder();
            String line;
            InputStream stream = DLAPIconn.getErrorStream();
            if (stream == null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(DLAPIconn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line + "\n");
                }
                reader.close();
                String dataPlayer = response.toString();

                // Collecting the data of the player
                int positionHardest = Integer.MAX_VALUE;
                JSONObject jsonPlayer = (JSONObject) JSONValue.parse(dataPlayer);
                JSONObject jsonPlayerData = (JSONObject) jsonPlayer.get("data");

                // Adding the name
                this.username = (String) jsonPlayerData.get("name");

                // Checking the records the player has submitted
                JSONArray playerRecords = (JSONArray) jsonPlayerData.get("records");
                for (int i = 0; i < playerRecords.size(); i++) {
                    JSONObject record = (JSONObject) playerRecords.get(i);
                    JSONObject demonData = (JSONObject) record.get("demon");
                    long position = (long) demonData.get("position");
                    boolean isPlayerApproved = (boolean) record.get("status").equals("approved");
                    if (isPlayerApproved && position <= 150) {
                        String name = (String) demonData.get("name");
                        long progress = (long) record.get("progress");
                        this.DLpoints += calculateDLpoints((int) position, (int) progress);
                        if (position < positionHardest) {
                            positionHardest = (int) position;
                            this.hardest = name;
                        }
                    }
                }

                // Checking the verifications the player has made
                JSONArray playerVerifications = (JSONArray) jsonPlayerData.get("verified");
                for (int i = 0; i < playerVerifications.size(); i++) {
                    JSONObject verification = (JSONObject) playerVerifications.get(i);
                    long position = (long) verification.get("position");
                    if (position <= 150) {
                        String name = (String) verification.get("name");
                        this.DLpoints += calculateDLpoints((int) position, 100);
                        if (position < positionHardest) {
                            positionHardest = (int) position;
                            this.hardest = name;
                        }
                    }
                }
            } else {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    String responseErr = scanner.next();
                    System.out.println(responseErr);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private double calculateDLpoints(int position, int progress) {
        double scoreDemon = 0.0;
        double scoreFinal;

        if (55 < position && position <= 150) {
            double a  = 6.273;
            scoreDemon = (56.191 * (Math.pow(2, ((54.147 - (position + 3.2)) * ((Math.log(50)) / 99))))) + a;
        } else if (35 < position && position <= 55) {
            double b = 1.036;   
            double c = 25.071;
            scoreDemon = (212.61 * (Math.pow(b, 1-position))) + c;
        } else if (20 < position && position <= 35) {
            double d = 1.0099685;
            double e = 31.152;
            scoreDemon = ((250-83.389) * (Math.pow(d, 2-position))) - e;
        } else if (0 < position && position <= 20) {
            double f = 1.168; 
            double g = 100.39;
            scoreDemon = ((250 - g) * (Math.pow(f, 1 - position))) + g;
        }

        if (progress != 100) {
            scoreFinal = (position <= 75) ? (scoreDemon * Math.pow(5,(progress - top75requirements[position - 1]) / (100 - top75requirements[position - 1]))) / 10 : 0;
        } else {
            scoreFinal = scoreDemon;
        }

        return scoreFinal;
    }
}
