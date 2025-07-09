package com.example.flashcardas.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashcardas.R;
import com.example.flashcardas.model.Deck;
import java.util.List;

/**
 * Adapter per la schermata "Home" che mostra i mazzi disponibili in una RecyclerView.
 * Ogni mazzo è rappresentato da un pulsante. Al clic sul pulsante, viene notificato
 * un listener esterno.
 */
public class HomeDeckAdapter extends RecyclerView.Adapter<HomeDeckAdapter.DeckViewHolder> {

    private List<Deck> deckList;
    private final OnDeckClickListener listener;

    /**
     * Interfaccia per gestire il click su un mazzo.
     */
    public interface OnDeckClickListener {
        void onDeckClick(Deck deck);
    }

    public HomeDeckAdapter(List<Deck> deckList, OnDeckClickListener listener) {
        this.deckList = deckList;
        this.listener = listener;
    }

    /**
     * Metodo per aggiornare la lista dei mazzi e rinfrescare la RecyclerView.
     */
    public void setDecks(List<Deck> decks) {
        this.deckList = decks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Associa il layout item_deck.xml a ogni elemento della lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        Deck deck = deckList.get(position);
        holder.deckButton.setText(deck.getName());

        // Click sul pulsante del mazzo: richiama il metodo dell'interfaccia
        holder.deckButton.setOnClickListener(v -> listener.onDeckClick(deck));
    }

    @Override
    public int getItemCount() {
        return deckList == null ? 0 : deckList.size();
    }

    /**
     * ViewHolder che rappresenta visivamente ogni mazzo come un pulsante.
     */
    static class DeckViewHolder extends RecyclerView.ViewHolder {
        Button deckButton;

        DeckViewHolder(View itemView) {
            super(itemView);
            deckButton = itemView.findViewById(R.id.deckButton);
        }
    }
}
