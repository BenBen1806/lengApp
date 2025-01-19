package com.example.lengapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OptionScreenActivity extends AppCompatActivity {

    /**
    using this variable to point on the filename json
     **/
    public static final String FILENAME_KEY = "file name key to .json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_screen);

        Intent dataPrev = getIntent();
        String fileName = dataPrev.getStringExtra(FILENAME_KEY);
        //on Click on Memorizing button
        ((Button)findViewById(R.id.btMemorize)).setOnClickListener(view -> {
            Intent intent = new Intent(this, MemorizingActivity.class);
            intent.putExtra(FILENAME_KEY, fileName);
            startActivity(intent);
        });
    }
}