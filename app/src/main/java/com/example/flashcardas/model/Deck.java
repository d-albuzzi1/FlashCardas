package com.example.flashcardas.model;

import java.util.List;

/**
 * Classe modello che rappresenta un mazzo (Deck) di flashcard.
 * Ogni Deck ha un identificativo univoco (id), un nome, e una lista di flashcard associate.
 *
 * Viene usata per memorizzare, manipolare e passare in giro le informazioni relative a un mazzo.
 */
public class Deck {
    private String id;
    private String name;

    private List<Flashcard> flashcards;

    // Costruttore vuoto necessario per alcune operazioni di serializzazione/deserializzazione (es. Firebase)
    public Deck() {
    }

    // Costruttore completo per creare direttamente un oggetto Deck con valori iniziali
    public Deck(String id, String name, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.flashcards = flashcards;
    }

    // Getter e setter per accedere e modificare i campi privati

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }
}
