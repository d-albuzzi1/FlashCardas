package com.example.flashcardas.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcardas.R;
import com.example.flashcardas.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializza ViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Inizializza BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Osserva i cambiamenti del Fragment corrente
        mainViewModel.getCurrentFragment().observe(this, this::loadFragment);

        // Mostra il Fragment di default se è la prima creazione
        if (savedInstanceState == null) {
            mainViewModel.setCurrentFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_home);  // Imposta l'item selezionato di default
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();

        switch (title) {
            case "Home":
                mainViewModel.setCurrentFragment(new HomeFragment());
                return true;
            case "Training":
                mainViewModel.setCurrentFragment(new TrainingFragment());
                return true;
            case "Manage":
                mainViewModel.setCurrentFragment(new ManageDecksFragment());
                return true;
            default:
                return false;
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

