package com.example.flashcardas.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardas.R;
import com.example.flashcardas.viewmodel.FlashcardViewModel;

public class MainActivity extends AppCompatActivity {
    private FlashcardViewModel flashcardViewModel;
    private RecyclerView recyclerView;
    private FlashcardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flashcardViewModel = new ViewModelProvider(this).get(FlashcardViewModel.class);

        flashcardViewModel.getFlashcards().observe(this, flashcards -> {
            adapter = new FlashcardAdapter(flashcards);
            recyclerView.setAdapter(adapter);
        });

        findViewById(R.id.addFlashcardButton).setOnClickListener(view -> {
            // Aprire dialog per inserire nuova parola
            flashcardViewModel.addFlashcard("Hello", "Ciao");
        });
    }
}

