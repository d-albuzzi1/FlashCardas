package com.example.flashcardas.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    private final FirebaseFirestore db;
    private final CollectionReference deckCollection;
    private final MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();
    private final MutableLiveData<Deck> deckLiveData = new MutableLiveData<>();

    public DeckRepository() {
        db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        deckCollection = db.collection("users").document(userId).collection("decks");
    }

    public LiveData<List<Deck>> getDecks() {
        deckCollection.addSnapshotListener((value, error) -> {
            if (error == null && value != null) {
                List<Deck> deckList = value.toObjects(Deck.class);
                decksLiveData.setValue(deckList);
            }
        });
        return decksLiveData;
    }
    public LiveData<Deck> getDeckById(String deckId) {
        deckCollection.document(deckId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Deck deck = documentSnapshot.toObject(Deck.class);
                deckLiveData.setValue(deck);
            }
        });
        return deckLiveData;
    }

    public void addDeck(Deck deck) {
        String id = deckCollection.document().getId();
        Deck deckWithId = new Deck(id, deck.getName(), deck.getFlashcards());
        deckCollection.document(id).set(deckWithId);
    }

    public void updateDeck(Deck deck) {
        deckCollection.document(deck.getId()).set(deck);
    }


    public void deleteDeck(String deckId) {
        deckCollection.document(deckId).delete();
    }

    public void addFlashcardToDeck(String deckId, Flashcard flashcard) {
        deckCollection.document(deckId).get().addOnSuccessListener(doc -> {
            Deck deck = doc.toObject(Deck.class);
            if (deck != null) {
                List<Flashcard> updatedList = new ArrayList<>(deck.getFlashcards());
                updatedList.add(flashcard);

                Deck updatedDeck = new Deck(deckId, deck.getName(), updatedList);
                deckCollection.document(deckId).set(updatedDeck);
            }
        });
    }

}
