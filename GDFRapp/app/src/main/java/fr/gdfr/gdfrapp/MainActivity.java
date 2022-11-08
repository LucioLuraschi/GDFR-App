package fr.gdfr.gdfrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //-------------------Update-------------------//
        Button redirectUpdateButton;
        Intent updateIntent;

        // Getting the elements
        redirectUpdateButton = (Button) findViewById(R.id.updateButton);
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
    }
}