package com.example.lengapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    public static List<Flashcard> loadFlashcardsFromJson(Activity activity, String fileName) {
        List<Flashcard> flashcardList = new ArrayList<>();
        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flashcardObject = jsonArray.getJSONObject(i);
                String word = flashcardObject.getString("word");
                String translation = flashcardObject.getString("translation");
                String soundFileName = flashcardObject.getString("soundFileName");

                flashcardList.add(new Flashcard(word, translation, soundFileName));
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error loading flashcards from JSON", e);
        }
        return flashcardList;
    }


    /**
     * Retrieves a list of JSON file names from the resources directory.
     *
     * @param context The application context.
     * @return A list of JSON file names.
     */
    public static List<String> getJsonFileNamesFromResources(Context context) {
        List<String> jsonFileNames = new ArrayList<>();
        try {
            // Get the list of files in the resources directory
            String[] fileNames = context.getAssets().list("");
            for (String fileName : fileNames) {
                if (fileName.endsWith(".json")) {
                    int dotIndex = fileName.indexOf('.');
                    StringBuilder sb = new StringBuilder(fileName);
                    sb.delete(dotIndex, fileName.length());
                    jsonFileNames.add(sb.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFileNames;
    }


}
