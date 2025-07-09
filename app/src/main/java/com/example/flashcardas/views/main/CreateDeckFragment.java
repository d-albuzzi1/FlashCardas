package com.example.flashcardas.views.main;

import static com.example.flashcardas.utils.IdUtils.generateId;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Deck;
import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.views.adapter.DeckEditAdapter;
import com.example.flashcardas.viewmodel.DeckViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment per creare o modificare un mazzo di flashcard.
 * Permette di inserire/modificare il nome del mazzo, aggiungere e rimuovere flashcard.
 * Utilizza un ViewModel condiviso per aggiornare e osservare lo stato del mazzo selezionato.
 */
public class CreateDeckFragment extends Fragment {
    private DeckViewModel deckViewModel;
    private EditText editTextDeckName;
    private RecyclerView recyclerView;
    private DeckEditAdapter adapter;
    private TextView textCardCount;

    private Button buttonAdd, buttonSaveDeck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inizializza la UI e componenti
        View view = inflater.inflate(R.layout.fragment_create_deck, container, false);

        editTextDeckName = view.findViewById(R.id.editTextDeckName);
        recyclerView = view.findViewById(R.id.recyclerViewFlashcardsInDeck);
        buttonAdd = view.findViewById(R.id.buttonAddFlashcard);
        buttonSaveDeck = view.findViewById(R.id.buttonSaveDeck);
        textCardCount = view.findViewById(R.id.textCardCount);


        // Adapter con listener per la rimozione delle flashcard
        adapter = new DeckEditAdapter(new ArrayList<>(), flashcard -> {
            Deck deck = deckViewModel.getSelectedDeck().getValue();
            if (deck != null) {
                List<Flashcard> updatedList = new ArrayList<>(deck.getFlashcards());
                updatedList.remove(flashcard);
                Deck updatedDeck = new Deck(deck.getId(), deck.getName(), updatedList);
                deckViewModel.setSelectedDeck(updatedDeck);
                deckViewModel.updateDeck(updatedDeck);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Ottiene il ViewModel condiviso
        deckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);

        // Osserva i cambiamenti del mazzo selezionato per aggiornare UI
        deckViewModel.getSelectedDeck().observe(getViewLifecycleOwner(), deck -> {
            if (deck != null) {
                editTextDeckName.setText(deck.getName());
                adapter.updateFlashcards(deck.getFlashcards());
                textCardCount.setText(deck.getFlashcards().size() + " carte");
            } else {
                editTextDeckName.setText("");
                adapter.updateFlashcards(new ArrayList<>());
                textCardCount.setText("0 carte");
            }
        });

        // Apre dialog per aggiungere una nuova flashcard
        buttonAdd.setOnClickListener(v -> showAddFlashcardDialog());

        // Salva o aggiorna il mazzo quando si preme il pulsante Salva
        buttonSaveDeck.setOnClickListener(v -> {
            String name = editTextDeckName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Inserisci un nome per il mazzo", Toast.LENGTH_SHORT).show();
                return;
            }

            Deck deck = deckViewModel.getSelectedDeck().getValue();

            if (deck == null) {
                // Crea un nuovo mazzo
                Deck newDeck = new Deck(generateId(), name, new ArrayList<>());
                deckViewModel.addDeck(newDeck);
                deckViewModel.setSelectedDeck(newDeck);
                Toast.makeText(getContext(), "Mazzo creato!", Toast.LENGTH_SHORT).show();

                // Resetta campi (opzionale)
                editTextDeckName.setText("");
                adapter.updateFlashcards(new ArrayList<>());

                // Torna indietro nella navigazione (opzionale)
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                // Aggiorna mazzo esistente
                Deck updatedDeck = new Deck(deck.getId(), name, deck.getFlashcards());
                deckViewModel.updateDeck(updatedDeck);
                deckViewModel.setSelectedDeck(updatedDeck);
                Toast.makeText(getContext(), "Mazzo aggiornato!", Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    // Mostra un dialog per inserire una nuova flashcard
    private void showAddFlashcardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Aggiungi Flashcard");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_flashcard, null);
        EditText editWord = dialogView.findViewById(R.id.editTextWord);
        EditText editTranslation = dialogView.findViewById(R.id.editTextTranslation);

        builder.setView(dialogView);
        builder.setPositiveButton("Aggiungi", (dialog, which) -> {
            String word = editWord.getText().toString().trim();
            String translation = editTranslation.getText().toString().trim();

            if (!word.isEmpty() && !translation.isEmpty()) {
                Deck deck = deckViewModel.getSelectedDeck().getValue();
                if (deck == null) {
                    // Crea nuovo mazzo se non esiste
                    deck = new Deck(generateId(), editTextDeckName.getText().toString().trim(), new ArrayList<>());
                    deckViewModel.setSelectedDeck(deck);
                    deckViewModel.addDeck(deck);
                }
                Flashcard flashcard = new Flashcard(word, translation);
                deckViewModel.addFlashcardToSelectedDeck(flashcard);

            } else {
                Toast.makeText(getContext(), "Compila entrambi i campi", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annulla", null);
        builder.show();
    }
}
