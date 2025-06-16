package com.example.flashcardas.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Flashcard;

import java.util.ArrayList;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcards = new ArrayList<>();

    // Costruttore
    public FlashcardAdapter(List<Flashcard> flashcards) {
        this.flashcards = new ArrayList<>(flashcards); // Copia per evitare riferimenti diretti
    }

    // Metodo per aggiornare i dati dell'adapter
    public void setFlashcards(List<Flashcard> newFlashcards) {
        this.flashcards.clear();
        this.flashcards.addAll(newFlashcards);
        notifyDataSetChanged(); // Notifica il cambiamento alla RecyclerView
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard currentFlashcard = flashcards.get(position);
        holder.questionText.setText(currentFlashcard.getWord());
        holder.answerText.setText(currentFlashcard.getTranslation());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    // ViewHolder per ogni flashcard
    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        TextView answerText;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question_text);
            answerText = itemView.findViewById(R.id.answer_text);
        }
    }
}
