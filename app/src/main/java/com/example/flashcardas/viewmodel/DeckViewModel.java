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

    // questo è da togliere quando metto i veri deck dell account
    private final MutableLiveData<List<Deck>> decks = new MutableLiveData<>(new ArrayList<>());


    public DeckViewModel() {
        deckRepository = new DeckRepository();
        decksLiveData = deckRepository.getDecks(); // Osserva i mazzi in tempo reale
        loadTestDecks();
    }

    private void loadTestDecks() {
        List<Deck> testDecks = new ArrayList<>();

        //  flashcard finte
        List<Flashcard> flashcards1 = new ArrayList<>();
        flashcards1.add(new Flashcard("Dog", "Cane"));
        flashcards1.add(new Flashcard("Cat", "Gatto"));

        List<Flashcard> flashcards2 = new ArrayList<>();
        flashcards2.add(new Flashcard("Eat", "Mangiare"));
        flashcards2.add(new Flashcard("Run", "Correre"));

        // Creiamo i Deck di prova con le Flashcard dentro
        testDecks.add(new Deck("1", "Animali", flashcards1));
        testDecks.add(new Deck("2", "Verbi", flashcards2));
        testDecks.add(new Deck("3", "Espressioni comuni", new ArrayList<>()));

        // Aggiorniamo i dati osservabili
        decks.setValue(testDecks);
    }

    public LiveData<List<Deck>> getDecks() {
        return decksLiveData;
    }

    // questo è da togliere quando metto i veri deck dell account
    public MutableLiveData<List<Deck>> getDecksMutable() {
        return decks;
    }

    public void addDeck(Deck deck) {
        deckRepository.addDeck(deck);
    }

    public void deleteDeck(String deckId) {
        deckRepository.deleteDeck(deckId);
    }
}
