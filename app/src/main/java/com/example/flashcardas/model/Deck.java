package com.example.flashcardas.model;

import java.util.List;

public class Deck {
    private String id;
    private String name;
    private List<Flashcard> flashcards;

    public Deck() {} // Costruttore vuoto richiesto da Firebase

    public Deck(String id, String name, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.flashcards = flashcards;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Flashcard> getFlashcards() { return flashcards; }
}
