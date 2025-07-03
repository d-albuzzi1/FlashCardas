package com.example.flashcardas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.repository.FlashcardRepository;

import java.util.List;

public class FlashcardViewModel extends ViewModel {

    private MutableLiveData<List<Flashcard>> flashcards;
    private FlashcardRepository repository;

    // chiamalo subito dopo la creazione del ViewModel, tipo in onCreate/onViewCreated
    public void init(Context context) {
        if (repository == null) {
            repository = new FlashcardRepository(context);
            flashcards = repository.getFlashcards();
        }
    }

    public LiveData<List<Flashcard>> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(String parola, String traduzione) {
        if (repository != null) {
            repository.addFlashcard(new Flashcard(parola, traduzione));
        }
    }
}
