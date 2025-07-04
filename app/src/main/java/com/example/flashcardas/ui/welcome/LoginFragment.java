package com.example.flashcardas.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashcardas.R;
import com.example.flashcardas.ui.main.MainActivity;
import com.example.flashcardas.viewmodel.AuthViewModel;

/**
 * Fragment per il login dell'utente.
 *
 * - Gestisce l'interfaccia con i campi email e password.
 * - Permette di avviare il login tramite AuthViewModel.
 * - Mostra messaggi di errore o successo tramite Toast.
 * - Consente di passare al fragment di registrazione.
 * - All'avvenuto login, apre MainActivity e chiude l'activity corrente.
 */

public class LoginFragment extends Fragment {
    private AuthViewModel authViewModel;
    private EditText emailInput, passwordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> loadRegisterFragment());

        observeViewModel();

        return view;
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Inserisci email e password", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.loginUser(email, password);
    }

    private void loadRegisterFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }


    private void observeViewModel() {

        authViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {

                Toast.makeText(getActivity(), "Login riuscito!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
