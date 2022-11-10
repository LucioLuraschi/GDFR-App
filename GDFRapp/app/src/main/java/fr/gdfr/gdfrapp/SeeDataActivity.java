package fr.gdfr.gdfrapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class SeeDataActivity extends AppCompatActivity {

    private final String urlToGetPlayers = "http://10.0.2.2:9000/players";
    private String playersData = "";
    public List<String> playerNames = new ArrayList<>();
    public String selectedPlayer = "";
    private Toast showSelectedPlayer;
    private Button consultPlayerStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showSelectedPlayer = Toast.makeText(this, "You didn't choose any player", Toast.LENGTH_LONG);
        ListView lv = findViewById(R.id.listviewnames);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPlayer = lv.getItemAtPosition(i).toString();
                System.out.println(selectedPlayer);
                showSelectedPlayer.setText("You have selected the player " + selectedPlayer);
                consultPlayerStats = findViewById(R.id.globalButtonData);
                consultPlayerStats.setText("See " + selectedPlayer + " data");
                showSelectedPlayer.show();
            }
        });
        // Getting the players
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlToGetPlayers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONArray players = (JSONArray) JSONValue.parse(response);
                        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
                            JSONObject player = (JSONObject) players.get(playerIndex);
                            String username = (String) player.get("username");
                            playerNames.add(username);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("b", error.toString());
            }
        });
        queue.add(stringRequest);

        // GitHub compte prof : wadael

        //-------------------Update-------------------//
        Button seeDataButton;
        Intent seeDataIntent;

        // Getting the elements
        seeDataButton = (Button) findViewById(R.id.globalButtonData);
        seeDataIntent = new Intent(this, PlayerResumeActivity.class);

        seeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPlayer.equals("")) {
                    showSelectedPlayer.show();
                    return;
                }
                seeDataIntent.putExtra("username", selectedPlayer);
                startActivity(seeDataIntent);
            }
        });

        //-------------------Search-------------------//

        SearchView searchView = findViewById(R.id.search_bar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,playerNames);
        lv.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}