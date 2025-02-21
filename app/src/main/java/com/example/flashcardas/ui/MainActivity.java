package com.example.flashcardas.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.flashcardas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ottieni il NavController dal nav_host_fragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Trova il BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Associa il BottomNavigationView al NavController
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}

