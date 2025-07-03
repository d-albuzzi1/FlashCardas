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

public class DeckEditAdapter extends RecyclerView.Adapter<DeckEditAdapter.ViewHolder> {

    private List<Flashcard> flashcards;
    private OnFlashcardDeleteListener deleteListener;

    public interface OnFlashcardDeleteListener {
        void onDelete(Flashcard flashcard);
    }

    public DeckEditAdapter(List<Flashcard> flashcards, OnFlashcardDeleteListener deleteListener) {
        this.flashcards = flashcards;
        this.deleteListener = deleteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView word, translation;
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.wordText);
            translation = itemView.findViewById(R.id.translationText);
            deleteButton = itemView.findViewById(R.id.deleteButton);

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
        // deleteButton sempre visibile
        holder.deleteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    public void updateFlashcards(List<Flashcard> newFlashcards) {
        this.flashcards = newFlashcards != null ? newFlashcards : new ArrayList<>();
        notifyDataSetChanged();
    }
}
