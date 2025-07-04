package com.example.flashcardas.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Deck;
import com.example.flashcardas.ui.adapter.ManageDeckAdapter;
import com.example.flashcardas.viewmodel.DeckViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment per gestire la lista dei mazzi.
 *
 * - Visualizza i mazzi tramite RecyclerView e ManageDeckAdapter.
 * - Osserva il DeckViewModel per aggiornare la lista dei mazzi in tempo reale.
 * - Permette di creare un nuovo mazzo tramite un pulsante.
 * - Consente di selezionare un mazzo per modificarlo o eliminarlo tramite un dialog.
 * - Gestisce la navigazione verso CreateDeckFragment per creare o modificare un mazzo.
 */

public class ManageDecksFragment extends Fragment {

    private DeckViewModel deckViewModel;
    private RecyclerView recyclerView;
    private ManageDeckAdapter adapter;
    private List<Deck> deckList = new ArrayList<>();
    private Button createDeckButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_decks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDecks);
        createDeckButton = view.findViewById(R.id.createButtonDeck);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ManageDeckAdapter(deckList, this::onDeckSelected);
        recyclerView.setAdapter(adapter);

        deckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);
        deckViewModel.getDecks().observe(getViewLifecycleOwner(), decks -> {
            adapter.setDecks(decks);
        });

        createDeckButton.setOnClickListener(v -> {
            openCreateDeckFragment(null);
        });

        return view;
    }

    private void onDeckSelected(Deck deck) {
        new AlertDialog.Builder(requireContext())
                .setTitle(deck.getName())
                .setItems(new CharSequence[]{"Modifica", "Elimina"}, (dialog, which) -> {
                    if (which == 0) {
                        deckViewModel.setSelectedDeck(deck);
                        openCreateDeckFragment(deck);
                    } else if (which == 1) {
                        deckViewModel.deleteDeck(deck.getId());
                        Toast.makeText(requireContext(), "Mazzo eliminato", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    private void openCreateDeckFragment(Deck deckToEdit) {
        CreateDeckFragment createDeckFragment = new CreateDeckFragment();
        if (deckToEdit != null) {
        } else {
            // Nuovo mazzo
            deckViewModel.setSelectedDeck(null);
        }
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createDeckFragment)
                .addToBackStack(null)
                .commit();
    }
}
