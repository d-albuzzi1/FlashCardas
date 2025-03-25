package com.example.flashcardas.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashcardas.R;
import com.example.flashcardas.model.Deck;
import java.util.List;

public class ManageDeckAdapter extends RecyclerView.Adapter<ManageDeckAdapter.DeckViewHolder> {
    private List<Deck> deckList;

    public ManageDeckAdapter(List<Deck> deckList) {
        this.deckList = deckList;
    }

    public void setDecks(List<Deck> decks) {
        this.deckList = decks;
        notifyDataSetChanged(); // Notifica alla RecyclerView che i dati sono cambiati
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        Deck deck = deckList.get(position);
        holder.deckNameTextView.setText(deck.getName());
    }

    @Override
    public int getItemCount() {
        return (deckList != null) ? deckList.size() : 0;
    }

    static class DeckViewHolder extends RecyclerView.ViewHolder {
        TextView deckNameTextView;

        DeckViewHolder(View itemView) {
            super(itemView);
            deckNameTextView = itemView.findViewById(R.id.textDeck);
        }
    }
}
