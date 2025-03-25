package com.example.flashcardas.ui.main;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Deck;
import com.example.flashcardas.ui.adapter.FlashcardAdapter;

public class TrainingFragment extends Fragment {

    private ViewPager2 viewPager;
    private FlashcardAdapter flashcardAdapter;
    private Deck deck;

    @NonNull
    public static TrainingFragment newInstance(Deck deck) {
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putParcelable("ARG_DECK", deck); // Passiamo l'intero oggetto Deck
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deck = getArguments().getParcelable("ARG_DECK");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);


        /*
        flashcards = new ArrayList<>();
        flashcards.add(new Flashcard("France", "Francia"));
        flashcards.add(new Flashcard("Boat", "Barca"));
        flashcards.add(new Flashcard("Bread", "Pane"));*/


        viewPager = rootView.findViewById(R.id.viewPager);
        flashcardAdapter = new FlashcardAdapter(deck.getFlashcards());
        viewPager.setAdapter(flashcardAdapter);

        return rootView;
    }
}
