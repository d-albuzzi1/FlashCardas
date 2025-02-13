package com.example.flashcardas.ui.welcome;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.flashcardas.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        String fragmentType = getIntent().getStringExtra("FRAGMENT_TYPE");

        if (fragmentType != null) {
            if (fragmentType.equals("login")) {
                loadFragment(new LoginFragment());
            } else if (fragmentType.equals("register")) {
                loadFragment(new RegisterFragment());
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, fragment)
                .commit();
    }
}
