package com.example.flashcardas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.repository.DeckRepository;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends AndroidViewModel {

    private DeckRepository deckRepository;
    private LiveData<List<Deck>> decksLiveData;
    private final MutableLiveData<Deck> selectedDeck = new MutableLiveData<>();

    public DeckViewModel(@NonNull Application application) {
        super(application);
        deckRepository = new DeckRepository(application.getApplicationContext());
        decksLiveData = deckRepository.getDecksLiveData();
    }

    public void addFlashcardToSelectedDeck(Flashcard flashcard) {
        Deck deck = selectedDeck.getValue();
        if (deck != null) {
            List<Flashcard> updatedFlashcards = new ArrayList<>(deck.getFlashcards());
            updatedFlashcards.add(flashcard);

            Deck updatedDeck = new Deck(deck.getId(), deck.getName(), updatedFlashcards);
            selectedDeck.setValue(updatedDeck);  // triggera osservatori
            deckRepository.addFlashcardToDeck(updatedDeck.getId(), flashcard);
        }
    }


    public void updateDeck(Deck deck) {
        deckRepository.updateDeck(deck);
    }

    public LiveData<List<Deck>> getDecks() {
        return decksLiveData;
    }

    public void setSelectedDeck(Deck deck) {
        selectedDeck.setValue(deck);
    }

    public LiveData<Deck> getSelectedDeck() {
        return selectedDeck;
    }

    public void getFlashcardsForDeck(String deckId, DeckRepository.OnFlashcardsLoadedListener listener) {
        deckRepository.getFlashcardsForDeck(deckId, listener);
    }

    public void addDeck(Deck deck) {
        deckRepository.addDeck(deck);
    }

    public void deleteDeck(String deckId) {
        deckRepository.deleteDeck(deckId);
    }
}
