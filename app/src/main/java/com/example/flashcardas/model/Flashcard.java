package com.example.flashcardas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Flashcard implements Parcelable {
    private String word;
    private String translation;

    // Costruttore
    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    // Costruttore per ricreare l'oggetto dal Parcel
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
