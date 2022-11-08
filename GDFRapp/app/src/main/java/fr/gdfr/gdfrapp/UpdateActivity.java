package fr.gdfr.gdfrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entities.Player;
import servers.BDDServer;
import servers.GDServer;

public class UpdateActivity extends AppCompatActivity {

    private TextView textViewEstimation;
    private TextView textViewNumberOfPlayers;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


    }
}