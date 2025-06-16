package com.example.flashcardas.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flashcardas.R;

public class CreateDeckFragment extends Fragment {

    private EditText word, translation, deckName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_deck, container, false); // Inflazione del layout

        /*
        // Inizializza i campi di input
        deckName = view.findViewById(R.id.editTextDeckName);
        word = view.findViewById(R.id.editTextWord);
        translation = view.findViewById(R.id.editTextTranslation);
        Button saveDeckButton = view.findViewById(R.id.buttonSaveDeck);

        saveDeckButton.setOnClickListener(v -> {
            String deckTitle = deckName.getText().toString().trim();
            String wordText = word.getText().toString().trim();
            String translationText = translation.getText().toString().trim();

            if (!deckTitle.isEmpty() && !wordText.isEmpty() && !translationText.isEmpty()) {
                // TODO: Salvare il mazzo su Firebase o localmente
            } else {
                // Mostrare un messaggio di errore
            }
        });
        */
        return view; // Restituisci la vista corretta
    }

}
