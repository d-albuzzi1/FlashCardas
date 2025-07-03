package com.example.flashcardas.model;

import java.util.List;

public class Deck {
    private String id;
    private String name;
    private List<Flashcard> flashcards;

    // Costruttore vuoto richiesto da Firestore
    public Deck() {
    }

    // Costruttore completo per quando vuoi creare tu un oggetto Deck
    public Deck(String id, String name, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.flashcards = flashcards;
    }

    // Getter e setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
