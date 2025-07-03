package com.example.flashcardas.repository;

import android.content.Context;

import com.example.flashcardas.model.Deck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalStorage {

    private static final String FILE_NAME = "decks.json";
    private Context context;
    private Gson gson = new Gson();

    public LocalStorage(Context context) {
        this.context = context;
    }

    public void saveDecks(List<Deck> decks) {
        String json = gson.toJson(decks);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Deck> loadDecks() {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<ArrayList<Deck>>(){}.getType();
            return gson.fromJson(sb.toString(), listType);

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();  // se file non esiste o errore, ritorna lista vuota
        }
    }
}
