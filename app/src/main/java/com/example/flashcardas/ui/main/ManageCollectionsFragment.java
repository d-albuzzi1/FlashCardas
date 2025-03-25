package com.example.flashcardas.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ManageCollectionsFragment extends Fragment {
    private DeckViewModel deckViewModel;
    private RecyclerView recyclerView;
    private ManageDeckAdapter adapter;
    private List<Deck> deckList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_collections, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDecks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ManageDeckAdapter(deckList);
        recyclerView.setAdapter(adapter);

        deckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);
        // Osserva i LiveData e aggiorna quando cambiano
        deckViewModel.getDecks().observe(getViewLifecycleOwner(), decks -> {
            if (decks != null) {
                deckList.clear();
                deckList.addAll(decks);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
