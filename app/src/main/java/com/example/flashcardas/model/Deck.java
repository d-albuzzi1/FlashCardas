package com.example.flashcardas.model;

import java.util.List;

public class Deck {
    private final String id;
    private final String name;
    private final List<Flashcard> flashcards;

    public Deck(String id, String name, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.flashcards = flashcards;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }
}
