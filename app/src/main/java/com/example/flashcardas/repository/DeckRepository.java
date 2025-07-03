package com.example.flashcardas.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DeckRepository {

    private final LocalStorage localStorage;
    private final FirebaseAuth auth;
    private final MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();


    public DeckRepository(Context context) {
        localStorage = new LocalStorage(context);
        auth = FirebaseAuth.getInstance();
        decksLiveData.setValue(localStorage.loadDecks());
    }

    // Se vuoi LiveData, puoi farlo così (caricamento sincronizzato)
    public LiveData<List<Deck>> getDecksLiveData() {
        return decksLiveData;
    }

    // Oppure metodo diretto che restituisce lista (più semplice)
    public List<Deck> getDecks() {
        return localStorage.loadDecks();
    }

    public void addDeck(Deck deck) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        decks.add(deck);
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks); // <--- aggiorna LiveData
    }

    public void updateDeck(Deck deck) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getId().equals(deck.getId())) {
                decks.set(i, deck);
                break;
            }
        }
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks); // <--- aggiorna LiveData
    }

    public void deleteDeck(String deckId) {
        List<Deck> decks = new ArrayList<>(localStorage.loadDecks());
        decks.removeIf(deck -> deck.getId().equals(deckId));
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks); // <--- aggiorna LiveData
    }

    public void addFlashcardToDeck(String deckId, Flashcard flashcard) {
        List<Deck> decks = localStorage.loadDecks();
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            if (deck.getId().equals(deckId)) {
                List<Flashcard> flashcards = new ArrayList<>(deck.getFlashcards());
                flashcards.add(flashcard);
                decks.set(i, new Deck(deckId, deck.getName(), flashcards));
                break;
            }
        }
        localStorage.saveDecks(decks);
        decksLiveData.setValue(decks); // <--- aggiorna LiveData
    }

    public void getFlashcardsForDeck(String deckId, OnFlashcardsLoadedListener listener) {
        List<Deck> decks = localStorage.loadDecks();
        for (Deck deck : decks) {
            if (deck.getId().equals(deckId)) {
                listener.onFlashcardsLoaded(deck.getFlashcards());
                return;
            }
        }
        listener.onFlashcardsLoaded(new ArrayList<>());
    }

    public interface OnFlashcardsLoadedListener {
        void onFlashcardsLoaded(List<Flashcard> flashcards);
    }
}
