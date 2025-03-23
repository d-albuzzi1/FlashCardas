package com.example.flashcardas.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcardas.R;
import com.example.flashcardas.ui.main.MainActivity;
import com.example.flashcardas.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cntrollo se l'utente è gia loggato
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        if (authViewModel.isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_welcome);

        String fragmentType = getIntent().getStringExtra("FRAGMENT_TYPE");

        if (fragmentType != null) {
            if (fragmentType.equals("login")) {
                loadFragment(new LoginFragment());
            } else if (fragmentType.equals("register")) {
                loadFragment(new RegisterFragment());
            }
        }else{
           loadFragment(new LoginFragment());
        }

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, fragment)
                .commit();
    }
}
