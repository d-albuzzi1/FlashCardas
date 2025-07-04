package com.example.flashcardas.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe modello che rappresenta una singola flashcard.
 * Ogni flashcard contiene una parola (word) e la sua traduzione (translation).
 *
 * Implementa l'interfaccia Parcelable per poter essere passata tra Activity o Fragment
 * tramite Intent o Bundle (utile ad esempio per trasferire i dati tra schermate).
 */
public class Flashcard implements Parcelable {
    // Parola originale (es. in inglese)
    private String word;

    // Traduzione della parola (es. in italiano)
    private String translation;

    // Costruttore vuoto (necessario per Firebase)
    public Flashcard() {
    }

    // Costruttore completo per creare una nuova flashcard
    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    // Costruttore usato da Android per ricreare l'oggetto da un Parcel
    protected Flashcard(Parcel in) {
        word = in.readString();
        translation = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(translation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // CREATOR richiesto da Parcelable per poter creare array e oggetti dal Parcel
    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };

    // Getter
    public String getWord() {
        return word;
    }
    public String getTranslation() {
        return translation;
    }
}
