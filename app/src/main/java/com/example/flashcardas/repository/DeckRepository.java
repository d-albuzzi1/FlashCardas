package com.example.flashcardas.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.flashcardas.model.Deck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        deckCollection.add(deck);
    }

    public void deleteDeck(String deckId) {
        deckCollection.document(deckId).delete();
    }
}
