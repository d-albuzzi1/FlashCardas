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

/**
 * FlashcardRepository gestisce il salvataggio e il caricamento delle flashcard
 * da un file locale in formato JSON. Usa LiveData per permettere l'osservazione
 * delle modifiche da parte della UI o della ViewModel.
 */
public class FlashcardRepository {

    private static final String FILE_NAME = "flashcards.json"; // Nome del file dove vengono salvate le flashcard
    private final Context context; // Context dell'app, usato per accedere al file system interno
    private final Gson gson = new Gson(); // Gson per serializzare e deserializzare JSON
    private final MutableLiveData<List<Flashcard>> flashcardsLiveData = new MutableLiveData<>();

    // Costruttore: inizializza il repository e carica le flashcard dal file
    public FlashcardRepository(Context context) {
        this.context = context;
        loadFlashcards();
    }

    // Carica le flashcard dal file e aggiorna il LiveData
    private void loadFlashcards() {
        List<Flashcard> loaded = loadFromFile();
        flashcardsLiveData.setValue(loaded);
    }

    // Restituisce il LiveData contenente la lista di flashcard
    public MutableLiveData<List<Flashcard>> getFlashcards() {
        return flashcardsLiveData;
    }

    // Aggiunge una nuova flashcard, salva su file e aggiorna il LiveData
    public void addFlashcard(Flashcard flashcard) {
        List<Flashcard> currentList = flashcardsLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(flashcard);
        saveToFile(currentList);
        flashcardsLiveData.setValue(currentList);
    }

    // Metodo che legge le flashcard da file JSON e le deserializza
    private List<Flashcard> loadFromFile() {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line); // Ricostruisce il contenuto del file riga per riga
            }

            Type listType = new TypeToken<ArrayList<Flashcard>>() {}.getType();
            List<Flashcard> list = gson.fromJson(sb.toString(), listType);
            return list != null ? list : new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Metodo che salva le flashcard attuali su file in formato JSON
    private void saveToFile(List<Flashcard> flashcards) {
        String json = gson.toJson(flashcards);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
