package fr.gdfr.gdfrapp;

import static fr.gdfr.gdfrapp.R.string.app_name;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import entities.Player;

public class SeeDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ListView lv = findViewById(R.id.listviewnames);

        //-------------------Update-------------------//
        Button seeDataButton;
        Intent seeDataIntent;

        // Getting the elements
        seeDataButton = (Button) findViewById(R.id.globalButtonData);
        seeDataIntent = new Intent(this, PlayerResumeActivity.class);

        seeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(seeDataIntent);
            }
        });

        //-------------------Search-------------------//

        SearchView searchView = findViewById(R.id.search_bar);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("test");
        arrayList.add("Malik");
        arrayList.add("Pierre");
        arrayList.add("Anakin");
        arrayList.add("Obi-wan");
        arrayList.add("Yoda");
//android:entries="@array/names"

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
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




    }
}