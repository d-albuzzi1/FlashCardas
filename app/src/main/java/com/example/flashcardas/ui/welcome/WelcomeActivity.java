package com.example.flashcardas.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcardas.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> openAuthActivity("login"));
        registerButton.setOnClickListener(v -> openAuthActivity("register"));
    }

    private void openAuthActivity(String fragmentType) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra("FRAGMENT_TYPE", fragmentType);
        startActivity(intent);
    }
}
