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

public class HomeDeckAdapter extends RecyclerView.Adapter<HomeDeckAdapter.DeckViewHolder> {
    private List<Deck> deckList;
    private final OnDeckClickListener listener;     // Interfaccia per gestire il click su un mazzo
    public interface OnDeckClickListener {
        void onDeckClick(Deck deck);
    }

    public HomeDeckAdapter(List<Deck> deckList, OnDeckClickListener listener) {
        this.deckList = deckList;
        this.listener = listener;
    }

    // Metodo per aggiornare la lista di mazzi e notificare l'adattatore
    public void setDecks(List<Deck> decks) {
        this.deckList = decks;
        notifyDataSetChanged(); // Notifica alla RecyclerView che i dati sono cambiati
    }

    // Metodo che crea un nuovo ViewHolder (oggetto che rappresenta un singolo elemento della lista)
    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate del layout dell'elemento della lista (item_deck.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new DeckViewHolder(view);
    }

    // Metodo che collega i dati del mazzo alla ViewHolder
    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {

        Deck deck = deckList.get(position);
        holder.deckButton.setText(deck.getName());

        holder.deckButton.setOnClickListener(v -> listener.onDeckClick(deck));
    }

    // Metodo che restituisce il numero di elementi nella lista
    @Override
    public int getItemCount() {
        return deckList == null ? 0 : deckList.size();
    }

    // Classe per rappresentare un elemento della RecyclerView
    static class DeckViewHolder extends RecyclerView.ViewHolder {
        Button deckButton; // Pulsante che rappresenta un mazzo

        // Costruttore della classe, collega la View all'oggetto Java
        DeckViewHolder(View itemView) {
            super(itemView);
            deckButton = itemView.findViewById(R.id.deckButton);
        }
    }
}
