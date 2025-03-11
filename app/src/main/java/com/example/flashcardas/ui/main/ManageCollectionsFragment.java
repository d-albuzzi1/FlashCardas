package com.example.flashcardas.ui.main;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.flashcardas.R;

public class ManageCollectionsFragment extends Fragment {
    private static final String ARG_DECK_ID = "deckId";

    public static ManageCollectionsFragment newInstance(String deckId) {
        ManageCollectionsFragment fragment = new ManageCollectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DECK_ID, deckId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_collections, container, false);
        TextView textView = view.findViewById(R.id.textViewDeck);
        String deckId = getArguments() != null ? getArguments().getString(ARG_DECK_ID) : "Unknown";
        textView.setText("Mazzo selezionato: " + deckId);
        return view;
    }
}
