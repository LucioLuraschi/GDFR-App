package fr.gdfr.gdfrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UpdateActivity extends AppCompatActivity {

    private Button addButton;
    private EditText playerName;
    private String url = "http://10.0.2.2:9000/players/";
    private Toast toastAlreadyInDatabase;
    private Toast toastPlayerSaved;
    private Toast toastPlayerSavedWithoutData;
    private Toast toastSaveFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        toastAlreadyInDatabase = Toast.makeText(this, "The player in already in the database", Toast.LENGTH_LONG);
        toastPlayerSaved = Toast.makeText(this, "The player has been saved in the database", Toast.LENGTH_LONG);
        toastPlayerSavedWithoutData = Toast.makeText(this, "The player has been in the database, but without data", Toast.LENGTH_LONG);
        toastSaveFailed = Toast.makeText(this, "The player has not been saved. Please try again", Toast.LENGTH_LONG);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addButton = findViewById(R.id.addTrigger);
        playerName = findViewById(R.id.playerUsername);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = playerName.getText().toString().trim();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url + username,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject responseObject = (JSONObject) JSONValue.parse(response);
                            Boolean isPlayerAlreadyInDatabase = (Boolean) responseObject.get("wasPlayerInDatabase");
                            System.out.println(response);
                            if (Boolean.TRUE.equals(isPlayerAlreadyInDatabase)) {
                                String playerUsername = (String) responseObject.get("username");
                                toastAlreadyInDatabase.setText(playerUsername + " is already in the database");
                                toastAlreadyInDatabase.show();
                                return;
                            }
                            Boolean isPlayerSavedWithData = (Boolean) responseObject.get("isGDDataSaved");
                            if (Boolean.TRUE.equals(isPlayerSavedWithData)) {
                                String playerUsername = (String) responseObject.get("username");
                                toastPlayerSaved.setText(playerUsername + " has been saved in the database");
                                toastPlayerSaved.show();
                                return;
                            }
                            Boolean isPlayerSaved = (Boolean) responseObject.get("isPlayerSaved");
                            if (Boolean.TRUE.equals(isPlayerSaved)) {
                                toastPlayerSavedWithoutData.setText(username + "has been saved in the database without data");
                                toastPlayerSavedWithoutData.show();
                                return;
                            }
                            toastSaveFailed.show();
                        }
                    }
                    , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Request", error.toString());
                        toastSaveFailed.show();
                    }
                });
                requestQueue.add(postRequest);

            }
        });
    }
}