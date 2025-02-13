package com.example.flashcardas.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.flashcardas.model.Flashcard;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FlashcardRepository {
    private FirebaseFirestore db;
    private MutableLiveData<List<Flashcard>> flashcards;

    public FlashcardRepository() {
        db = FirebaseFirestore.getInstance();
        flashcards = new MutableLiveData<>();
        loadFlashcards();
    }

    public void loadFlashcards() {
        db.collection("flashcards").addSnapshotListener((querySnapshot, error) -> {
            if (error == null && querySnapshot != null) {
                List<Flashcard> lista = new ArrayList<>();
                for (DocumentSnapshot doc : querySnapshot) {
                    lista.add(doc.toObject(Flashcard.class));
                }
                flashcards.setValue(lista);
            }
        });
    }

    public MutableLiveData<List<Flashcard>> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(Flashcard flashcard) {
        db.collection("flashcards").add(flashcard);
    }
}
