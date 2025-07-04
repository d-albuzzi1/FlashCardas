package com.example.flashcardas.ui.adapter;

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
 * Adapter per la RecyclerView che mostra la lista dei mazzi nella schermata di gestione.
 * Ogni mazzo è rappresentato da un pulsante con il nome, e la selezione di un mazzo
 * è notificata tramite un listener esterno.
 */
public class ManageDeckAdapter extends RecyclerView.Adapter<ManageDeckAdapter.DeckViewHolder> {

    /**
     * Interfaccia per gestire il click su un mazzo.
     */
    public interface OnDeckClickListener {
        void onDeckClick(Deck deck);
    }

    private List<Deck> deckList;
    private OnDeckClickListener listener;

    public ManageDeckAdapter(List<Deck> deckList, OnDeckClickListener listener) {
        this.deckList = deckList;
        this.listener = listener;
    }

    /**
     * Aggiorna la lista dei mazzi e notifica il cambiamento alla RecyclerView.
     */
    public void setDecks(List<Deck> decks) {
        this.deckList = decks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflazione del layout per ogni elemento della lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        Deck deck = deckList.get(position);
        holder.deckButton.setText(deck.getName());

        // Imposta il click listener sull'intero itemView (non solo sul pulsante)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeckClick(deck);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (deckList != null) ? deckList.size() : 0;
    }

    /**
     * ViewHolder che mantiene il riferimento al pulsante del mazzo.
     */
    static class DeckViewHolder extends RecyclerView.ViewHolder {
        Button deckButton;

        DeckViewHolder(View itemView) {
            super(itemView);
            deckButton = itemView.findViewById(R.id.deckButton);
        }
    }
}
