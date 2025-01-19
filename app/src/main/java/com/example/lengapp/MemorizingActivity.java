package com.example.lengapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

public class MemorizingActivity extends AppCompatActivity {

    private List<Flashcard> flashcards;
    private int currentFlashcardIndex = 0;
    private MediaPlayer mediaPlayer;

    private TextView wordTextView, translateTextView;
    private Button playSoundButton, saveButton, nextButton, translationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorizing);



        // Initialize views
        wordTextView = findViewById(R.id.wordTextView);
        translateTextView = findViewById(R.id.translateTextView);
        playSoundButton = findViewById(R.id.playSoundButton);
        saveButton = findViewById(R.id.saveButton);
        nextButton = findViewById(R.id.nextButton);
        translationButton = findViewById(R.id.translationButton);

        // Load flashcards from JSON
        flashcards = FlashcardUtils.loadFlashcardsFromJson(this, "flashcards.json");

        // Display the first flashcard
        displayFlashcard();

        // Play sound button logic
        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playSound();
            }
        });

        // Next button logic
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextFlashcard();
            }
        });

        // Translation button logic
        translationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTranslation();
            }
        });

        // Save button logic
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFlashcards();
            }
        });
    }

    /**
     * Load flashcards from JSON stored in the assets folder.
     */
//    private List<Flashcard> loadFlashcardsFromJson() {
//        List<Flashcard> flashcardList = new ArrayList<>();
//        try {
//            InputStream inputStream = getAssets().open("flashcards.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//
//            String json = new String(buffer, StandardCharsets.UTF_8);
//            JSONArray jsonArray = new JSONArray(json);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject flashcardObject = jsonArray.getJSONObject(i);
//                String word = flashcardObject.getString("word");
//                String translation = flashcardObject.getString("translation");
//                String soundFileName = flashcardObject.getString("soundFileName");
//
//                flashcardList.add(new Flashcard(word, translation, soundFileName));
//            }
//        } catch (IOException | JSONException e) {
//            Log.e("MemorizingActivity", "Error loading flashcards from JSON", e);
//        }
//        return flashcardList;
//    }

    /**
     * Display the current flashcard on the screen.
     */
    private void displayFlashcard() {
        if (flashcards.isEmpty()) {
            wordTextView.setText("No flashcards available");
            return;
        }
        Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
        wordTextView.setText(currentFlashcard.getWord());
        translateTextView.setText(currentFlashcard.getTranslation());
    }

    /**
     * Show the next flashcard in the list.
     */
    private void showNextFlashcard() {
        if (flashcards.isEmpty()) return;

        currentFlashcardIndex = (currentFlashcardIndex + 1) % flashcards.size();
        displayFlashcard();
    }

    /**
     * Show the translation of the current flashcard.
     */
    private void showTranslation() {
        if (flashcards.isEmpty()) return;

        Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
        Toast.makeText(this, currentFlashcard.getTranslation(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Play the sound for the current flashcard.
     */
    private void playSound() {
        if (flashcards.isEmpty()) return;

        Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
        int soundResourceId = getResources().getIdentifier(
                currentFlashcard.getSoundFileName(), "raw", getPackageName()
        );

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        } else {
            Toast.makeText(this, "Sound file not found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save the list of flashcards to a JSON file.
     */
    private void saveFlashcards() {
        FlashcardUtils.saveFlashcardsToJson(this, flashcards, "flashcards.json");
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


}
