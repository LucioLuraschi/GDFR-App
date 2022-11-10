package fr.gdfr.gdfrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //-------------------Update-------------------//
        Button redirectUpdateButton;
        Intent updateIntent;

        // Getting the elements
        redirectUpdateButton = (Button) findViewById(R.id.addPlayerButton);
        updateIntent = new Intent(this, UpdateActivity.class);

        redirectUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(updateIntent);
            }
        });

        //-------------------Update-------------------//
        Button seeDataButton;
        Intent seeDataIntent;

        // Getting the elements
        seeDataButton = (Button) findViewById(R.id.globalButtonData);
        seeDataIntent = new Intent(this, SeeDataActivity.class);

        seeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(seeDataIntent);
            }
        });

        //-------------------Update-------------------//
        Button seeUpdateButton;
        Toast messageSoon = Toast.makeText(this,
                "This functionality will be implemented soon", Toast.LENGTH_LONG);

        // Getting the elements
        seeUpdateButton = (Button) findViewById(R.id.updateButton);
        seeUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageSoon.show();
            }
        });
    }
}