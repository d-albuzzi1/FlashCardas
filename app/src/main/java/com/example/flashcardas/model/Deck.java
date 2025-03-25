package com.example.flashcardas.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Deck implements Parcelable {
    private final String id;
    private final String name;
    private final List<Flashcard> flashcards;

    public Deck(String id, String name, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.flashcards = flashcards;
    }

    protected Deck(Parcel in) {
        id = in.readString();
        name = in.readString();
        flashcards = in.createTypedArrayList(Flashcard.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(flashcards);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

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
