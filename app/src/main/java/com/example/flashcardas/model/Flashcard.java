package com.example.flashcardas.model;

public class Flashcard {
    private String word;
    private String translation;

    // Costruttore, getter e setter
    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}

