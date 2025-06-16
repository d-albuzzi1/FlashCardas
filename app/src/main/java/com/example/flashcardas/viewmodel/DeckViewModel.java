package com.example.flashcardas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.repository.DeckRepository;

import java.util.ArrayList;
import java.util.List;

public class DeckViewModel extends ViewModel {
    private final DeckRepository deckRepository;
    private final LiveData<List<Deck>> decksLiveData;
    private final MutableLiveData<Deck> selectedDeck = new MutableLiveData<>();


    public DeckViewModel() {
        deckRepository = new DeckRepository();
        decksLiveData = deckRepository.getDecks();
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

    public LiveData<Deck> getDeckById(String deckId) {
        return deckRepository.getDeckById(deckId);
    }

    public void addDeck(Deck deck) {
        deckRepository.addDeck(deck);
    }

    public void deleteDeck(String deckId) {
        deckRepository.deleteDeck(deckId);
    }
}
