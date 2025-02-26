package com.example.flashcardas.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Flashcard;

import java.util.ArrayList;
import java.util.List;

public class TrainingFragment extends Fragment {

    private ViewPager2 viewPager;
    private FlashcardAdapter flashcardAdapter;
    private List<Flashcard> flashcards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);


        flashcards = new ArrayList<>();
        flashcards.add(new Flashcard("France", "Francia"));
        flashcards.add(new Flashcard("Boat", "Barca"));
        flashcards.add(new Flashcard("Bread", "Pane"));


        viewPager = rootView.findViewById(R.id.viewPager);
        flashcardAdapter = new FlashcardAdapter(flashcards);
        viewPager.setAdapter(flashcardAdapter);

        return rootView;
    }
}
