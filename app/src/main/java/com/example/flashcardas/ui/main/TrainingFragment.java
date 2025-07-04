package com.example.flashcardas.ui.main;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcardas.R;
import com.example.flashcardas.model.Flashcard;
import com.example.flashcardas.viewmodel.DeckViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainingFragment extends Fragment {
    private static final String ARG_DECK_ID = "deck_id";
    private String deckId;

    private List<Flashcard> allFlashcards = new ArrayList<>();
    private Set<Flashcard> knownFlashcards = new HashSet<>();
    private List<Flashcard> remaining = new ArrayList<>();

    private int currentIndex = 0;
    private boolean showingWord = true;
    private TextView wordText;
    private TextView translationText;
    private ImageView red_arrow;
    private ImageView green_arrow;
    private View cardView;
    private View finishLayout;
    private View restartButton;
    private DeckViewModel deckViewModel;


    public static TrainingFragment newInstance(String deckId) {
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DECK_ID, deckId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deckId = getArguments().getString(ARG_DECK_ID);
            // TODO: usa deckId per caricare le carte del mazzo
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordText = view.findViewById(R.id.flashcard_word);
        translationText = view.findViewById(R.id.text_translation);
        cardView = view.findViewById(R.id.flashcard_view);
        finishLayout = view.findViewById(R.id.finish_layout);
        restartButton = view.findViewById(R.id.button_restart);
        green_arrow = view.findViewById(R.id.green_arrow);
        red_arrow = view.findViewById(R.id.red_arrow);

        deckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);

        deckViewModel.getFlashcardsForDeck(deckId, flashcards -> {
            allFlashcards = flashcards;
            showNextCard();
        });

        setSwipeGesture(view);

        restartButton.setOnClickListener(v -> restartTraining());
    }

    private void showNextCard() {
        remaining = allFlashcards.stream()
                .filter(card -> !knownFlashcards.contains(card))
                .collect(Collectors.toList());

        if (remaining.isEmpty()) {
            // Nascondi la card, mostra messaggio e bottone
            cardView.setVisibility(View.GONE);
            translationText.setVisibility(View.GONE);
            red_arrow.setVisibility(View.GONE);
            green_arrow.setVisibility(View.GONE);

            finishLayout.setVisibility(View.VISIBLE);
            restartButton.setVisibility(View.VISIBLE);
            return;
        }

        red_arrow.setVisibility(View.VISIBLE);
        green_arrow.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        finishLayout.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);

        currentIndex = new Random().nextInt(remaining.size());
        Flashcard currentCard = remaining.get(currentIndex);
        wordText.setText(currentCard.getWord());
        translationText.setText(currentCard.getTranslation());
        translationText.setVisibility(View.GONE);
        showingWord = true;
    }

    private void restartTraining() {
        knownFlashcards.clear();
        currentIndex = 0;
        showNextCard();
    }

    private void showTranslation() {
        translationText.setAlpha(0f);
        translationText.setVisibility(View.VISIBLE);
        translationText.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }



    private void setSwipeGesture(View rootView) {
        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // Questo gestisce il TAP singolo
                showTranslation(); // metodo per mostrare la traduzione
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        handleSwipeRight();
                    } else {
                        handleSwipeLeft();
                    }
                    return true;
                }
                return false;
            }
        });

        // Imposta il touch listener direttamente sulla card
        cardView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }


    private void handleSwipeRight() {
        if (remaining.isEmpty()) return; // sicurezza

        knownFlashcards.add(remaining.get(currentIndex)); // usa remaining, non allFlashcards!
        animateCard(true);
    }

    private void handleSwipeLeft() {
        animateCard(false);
    }

    private void animateCard(boolean toRight) {
        float toX = toRight ? 1000f : -1000f;
        cardView.animate()
                .translationX(toX)
                .alpha(0f)
                .setDuration(300)
                .withEndAction(() -> {
                    cardView.setTranslationX(0f);
                    cardView.setAlpha(1f);
                    showNextCard();
                })
                .start();
    }
}