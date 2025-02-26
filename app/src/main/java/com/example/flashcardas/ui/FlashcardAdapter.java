package com.example.flashcardas.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Flashcard;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcards;

    // Costruttore
    public FlashcardAdapter(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    // Inflazione del layout per ogni elemento (flashcard)
    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(itemView);
    }

    // Binding dei dati alla vista
    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard currentFlashcard = flashcards.get(position);
        holder.questionText.setText(currentFlashcard.getWord());
        holder.answerText.setText(currentFlashcard.getTranslation());
    }

    // Numero di elementi da visualizzare
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

