package com.example.lengapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private ListView listViewButtons;
    private List<String> buttonLabels;
    private ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        showInputDialog(this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //disableing ''dark mode''
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        listViewButtons = findViewById(R.id.listViewButtons);

        // Example list of button labels
        buttonLabels = new ArrayList<>();


        // Create an ArrayAdapter to display buttons
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, buttonLabels);
        listViewButtons.setAdapter(adapter);

        // Set onClick listener for each button in the ListView
        listViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lengName = buttonLabels.get(position);
                Intent intent = new Intent(MainActivity.this, OptionScreenActivity.class);
                intent.putExtra(OptionScreenActivity.FILENAME_KEY, lengName);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        buttonLabels.clear();
        List<String> filesNames = FlashcardUtils.getJsonFileNamesFromResources(this);
        buttonLabels.addAll(filesNames);
        adapter.notifyDataSetChanged();
    }

    public void showInputDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout layout = new LinearLayout(context);
        EditText input = new EditText(context);
        Button button = new Button(context);

        // Set up the input field
        input.setHint("Enter a lenguege");
        layout.addView(input);

        // Set up the button
        button.setText("Submit");
        layout.addView(button);

        // Add the layout to the dialog
        builder.setView(layout);
        builder.setTitle("Enter a new language");

        // Set up the button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                // Do something with the text
                System.out.println("Text entered: " + text);
            }
        });

        // Show the dialog
        builder.show();
    }

}
