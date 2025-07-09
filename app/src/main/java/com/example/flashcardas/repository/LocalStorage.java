package com.example.flashcardas.repository;

import android.content.Context;

import com.example.flashcardas.model.Deck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe LocalStorage gestisce il salvataggio e il caricamento dei mazzi (Deck)
 * in un file JSON nella memoria interna dell'app.
 * Viene utilizzata per persistere i dati anche dopo la chiusura dell'app.
 */
public class LocalStorage {

    private static final String FILE_NAME = "decks.json";  // Nome del file JSON dove vengono salvati i mazzi
    private Context context;                               // Context per accedere al file system
    private Gson gson = new Gson();                        // Gson per serializzare/deserializzare oggetti

    public LocalStorage(Context context) {
        this.context = context;
    }

    /**
     * Salva una lista di Deck nel file locale come JSON.
     * @param decks Lista di mazzi da salvare
     */
    public void saveDecks(List<Deck> decks) {
        String json = gson.toJson(decks);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica la lista di Deck dal file locale.
     * @return Lista di mazzi (vuota se file non esiste o errore)
     */
    public List<Deck> loadDecks() {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<ArrayList<Deck>>() {}.getType();
            return gson.fromJson(sb.toString(), listType);

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
