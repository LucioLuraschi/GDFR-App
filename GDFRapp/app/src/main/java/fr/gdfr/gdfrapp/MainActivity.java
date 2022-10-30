package fr.gdfr.gdfrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button redirectUpdateButton;
    private Intent updateIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Making the intent

        // Getting the elements
        redirectUpdateButton = (Button) findViewById(R.id.updateButton);
        updateIntent = new Intent(this, UpdateActivity.class);

        redirectUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(updateIntent);
            }
        });
    }
}