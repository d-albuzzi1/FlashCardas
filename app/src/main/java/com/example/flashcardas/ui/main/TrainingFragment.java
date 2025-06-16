package com.example.flashcardas.ui.main;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flashcardas.R;
import com.example.flashcardas.ui.adapter.FlashcardAdapter;
import com.example.flashcardas.viewmodel.DeckViewModel;

import java.util.ArrayList;


public class TrainingFragment extends Fragment {

    private FlashcardAdapter flashcardAdapter;
    private DeckViewModel deckViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);
        ViewPager2 viewPager = rootView.findViewById(R.id.viewPager);

        // Inizializza l'adapter con una lista vuota
        flashcardAdapter = new FlashcardAdapter(new ArrayList<>());
        viewPager.setAdapter(flashcardAdapter);

        if (getArguments() != null) {
            String deckId = getArguments().getString("ARG_DECK_ID");
            deckViewModel.getDeckById(deckId).observe(getViewLifecycleOwner(), selectedDeck -> {
                if (selectedDeck != null) {
                    // Aggiorna l'adapter con le nuove flashcard
                    flashcardAdapter.setFlashcards(selectedDeck.getFlashcards());
                }
            });
        }

        return rootView;
    }
}