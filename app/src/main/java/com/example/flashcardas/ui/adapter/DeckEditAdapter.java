package com.example.flashcardas.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter per il RecyclerView che mostra e gestisce le flashcard all'interno
 * della schermata di modifica di un mazzo. Ogni elemento visualizza una parola,
 * la sua traduzione e un pulsante per eliminarla.
 */
public class DeckEditAdapter extends RecyclerView.Adapter<DeckEditAdapter.ViewHolder> {

    private List<Flashcard> flashcards;
    private OnFlashcardDeleteListener deleteListener;

    /**
     * Interfaccia per notificare l'eliminazione di una flashcard.
     */
    public interface OnFlashcardDeleteListener {
        void onDelete(Flashcard flashcard);
    }

    public DeckEditAdapter(List<Flashcard> flashcards, OnFlashcardDeleteListener deleteListener) {
        this.flashcards = flashcards;
        this.deleteListener = deleteListener;
    }

    /**
     * ViewHolder che contiene i riferimenti ai componenti della UI della singola flashcard.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView word, translation;
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.wordText);
            translation = itemView.findViewById(R.id.translationText);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            // Gestione del click sul pulsante di eliminazione
            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && deleteListener != null) {
                    deleteListener.onDelete(flashcards.get(position));
                }
            });
        }
    }

    @Override
    public DeckEditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeckEditAdapter.ViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.word.setText(flashcard.getWord());
        holder.translation.setText(flashcard.getTranslation());
        holder.deleteButton.setVisibility(View.VISIBLE);  // sempre visibile
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    /**
     * Metodo per aggiornare l'elenco di flashcard e notificare la RecyclerView.
     */
    public void updateFlashcards(List<Flashcard> newFlashcards) {
        this.flashcards = newFlashcards != null ? newFlashcards : new ArrayList<>();
        notifyDataSetChanged();
    }
}
