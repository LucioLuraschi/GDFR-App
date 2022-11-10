package fr.gdfr.gdfrapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import entities.Player;

public class PlayerResumeActivity extends AppCompatActivity {

    private Player player = new Player("");
    private String urlToGetPlayerData = "http://10.0.2.2:9000/players/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String playerUsername = intent.getStringExtra("username");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, (urlToGetPlayerData + playerUsername),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONObject playerData = (JSONObject) JSONValue.parse(response);
                        String username = (String) playerData.get("username");
                        player.setUsername(username);

                        // Setting the GD Data
                        Long idGD = (Long) playerData.get("idGD");
                        if (idGD != null) {
                            player.setIdGD(idGD.intValue());
                            JSONObject statsgd = (JSONObject) playerData.get("statsGD");
                            int stars = ((Long) statsgd.get("stars")).intValue();
                            int diamonds = ((Long) statsgd.get("diamonds")).intValue();
                            int secretCoins = ((Long) statsgd.get("secretCoins")).intValue();
                            int userCoins = ((Long) statsgd.get("userCoins")).intValue();
                            int demons = ((Long) statsgd.get("demons")).intValue();
                            int cps = ((Long) statsgd.get("cps")).intValue();
                            Long gotRank = (Long) statsgd.get("rank");
                            int rank = (gotRank != null) ? gotRank.intValue() : 0;
                            player.setGDStats(stars, diamonds, secretCoins, userCoins, demons, cps, rank);
                        }

                        // Setting the DL Data
                        Long idDL = (Long) playerData.get("idDL");
                        if (idDL != null) {
                            player.setIdDL(idDL.intValue());
                            JSONObject statsDL = (JSONObject) playerData.get("statsDL");
                            String hardestDL = (String) statsDL.get("hardest");
                            int pointsDL = ((Long) statsDL.get("points")).intValue();
                            player.setDemonListStats(hardestDL, pointsDL);
                        }

                        // Putting the data in the activity
                        TextView playerName = findViewById(R.id.AjoutTitle);
                        playerName.setText(player.getUsername());

                        TextView statsGD = findViewById(R.id.GDStats);
                        String gdStatsText = (player.getIdGD() == 0) ? "No data for this player" : player.gdStatsToView();
                        statsGD.setText(gdStatsText);

                        TextView statsDL = findViewById(R.id.DLStats);
                        String dlStatsText = (player.getIdDL() == 0) ? "No data for this player" : player.dlStatsToView();
                        statsDL.setText(dlStatsText);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("b", error.toString());
            }
        });
        queue.add(stringRequest);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImageView imageView = findViewById(R.id.imageView) ;

    }
}