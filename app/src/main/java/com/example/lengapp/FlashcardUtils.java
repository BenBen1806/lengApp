package com.example.lengapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FlashcardUtils {

    private static final String TAG = "FlashcardUtils";

    /**
     * Save a list of flashcards to a JSON file in the app's internal storage.
     *
     * @param context    The application context.
     * @param flashcards The list of flashcards to save.
     * @param fileName   The name of the JSON file.
     */
    public static void saveFlashcardsToJson(Context context, List<Flashcard> flashcards, String fileName) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (Flashcard flashcard : flashcards) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("word", flashcard.getWord());
                jsonObject.put("translation", flashcard.getTranslation());
                jsonObject.put("soundFileName", flashcard.getSoundFileName());

                jsonArray.put(jsonObject);
            }

            String jsonString = jsonArray.toString();
            writeToFile(context, jsonString, fileName);

            Log.d(TAG, "Flashcards saved successfully to " + fileName);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
        }
    }

    /**
     * Write a string to a file in the app's internal storage.
     *
     * @param context  The application context.
     * @param data     The data to write.
     * @param fileName The name of the file.
     */
    private static void writeToFile(Context context, String data, String fileName) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to file", e);
        }
    }

}
