package com.example.flashcardas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.repository.FlashcardRepository;

import java.util.List;

public class FlashcardViewModel extends ViewModel {
    private MutableLiveData<List<Flashcard>> flashcards;
    private FlashcardRepository repository;

    public FlashcardViewModel() {
        repository = new FlashcardRepository();
        flashcards = repository.getFlashcards();
    }

    public LiveData<List<Flashcard>> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(String parola, String traduzione) {
        repository.addFlashcard(new Flashcard(parola, traduzione));
    }
}
