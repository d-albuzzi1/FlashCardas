package com.example.flashcardas.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.flashcardas.model.Flashcard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FlashcardRepository {

    private static final String FILE_NAME = "flashcards.json";
    private final Context context;
    private final Gson gson = new Gson();
    private final MutableLiveData<List<Flashcard>> flashcardsLiveData = new MutableLiveData<>();

    public FlashcardRepository(Context context) {
        this.context = context;
        loadFlashcards();
    }

    private void loadFlashcards() {
        List<Flashcard> loaded = loadFromFile();
        flashcardsLiveData.setValue(loaded);
    }

    public MutableLiveData<List<Flashcard>> getFlashcards() {
        return flashcardsLiveData;
    }

    public void addFlashcard(Flashcard flashcard) {
        List<Flashcard> currentList = flashcardsLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(flashcard);
        saveToFile(currentList);
        flashcardsLiveData.setValue(currentList);
    }

    private List<Flashcard> loadFromFile() {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Type listType = new TypeToken<ArrayList<Flashcard>>() {}.getType();
            List<Flashcard> list = gson.fromJson(sb.toString(), listType);
            return list != null ? list : new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Flashcard> flashcards) {
        String json = gson.toJson(flashcards);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
