package com.example.flashcardas.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsabile della gestione dei mazzi (Deck) e delle flashcard.
 * Si appoggia su un sistema di storage locale (LocalStorage) e usa LiveData
 * per permettere aggiornamenti in tempo reale della UI (es. lista dei mazzi).
 */
public class DeckRepository {

    private final LocalStorage localStorage; // Gestisce il salvataggio/caricamento locale
    private final MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>(); // LiveData osservabile per la UI

    // Costruttore: inizializza localStorage e LiveData con i dati salvati
    public DeckRepository(Context context) {
        localStorage = new LocalStorage(context);

        decksLiveData.setValue(localStorage.loadDecks());
    }

    // Restituisce LiveData della lista dei mazzi: utile per observer da ViewModel o UI
    public LiveData<List<Deck>> getDecksLiveData() {
        return decksLiveData;
    }

    // Aggiunge un nuovo mazzo e aggiorna il LiveData
    public void addDeck(Deck deck) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        decks.add(deck);
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks); // Notifica gli observer
    }

    // Modifica un mazzo esistente (basato su ID) e aggiorna il LiveData
    public void updateDeck(Deck deck) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getId().equals(deck.getId())) {
                decks.set(i, deck);
                break;
            }
        }
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks);
    }

    // Elimina un mazzo dato il suo ID e aggiorna il LiveData
    public void deleteDeck(String deckId) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        decks.removeIf(deck -> deck.getId().equals(deckId));
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks);
    }

    // Aggiunge una flashcard a un mazzo specifico
    public void addFlashcardToDeck(String deckId, Flashcard flashcard) {
        List<Deck> decks = localStorage.loadDecks();
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            if (deck.getId().equals(deckId)) {
                List<Flashcard> flashcards = new ArrayList<>(deck.getFlashcards());
                flashcards.add(flashcard);
                decks.set(i, new Deck(deckId, deck.getName(), flashcards)); // Crea un nuovo oggetto Deck aggiornato
                break;
            }
        }
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks);
    }

    // Recupera le flashcard di un mazzo usando un callback
    public void getFlashcardsForDeck(String deckId, OnFlashcardsLoadedListener listener) {
        List<Deck> decks = localStorage.loadDecks();
        for (Deck deck : decks) {
            if (deck.getId().equals(deckId)) {
                listener.onFlashcardsLoaded(deck.getFlashcards()); // Callback con i dati trovati
                return;
            }
        }
        listener.onFlashcardsLoaded(new ArrayList<>()); // Mazzo non trovato → lista vuota
    }

    // Interfaccia callback per restituire le flashcard (pattern asincrono)
    public interface OnFlashcardsLoadedListener {
        void onFlashcardsLoaded(List<Flashcard> flashcards);
    }
}
