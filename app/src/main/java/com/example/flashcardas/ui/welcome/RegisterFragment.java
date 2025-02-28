package com.example.flashcardas.ui.welcome;

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
import com.example.flashcardas.viewmodel.AuthViewModel;
import com.example.flashcardas.viewmodel.MainViewModel;

public class RegisterFragment extends Fragment {
    private MainViewModel mainViewModel;
    private EditText emailInput, passwordInput, confirmPasswordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput);
        Button registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();
            mainViewModel.registerUser(email, password, confirmPassword);
        });


        observeViewModel();

        return view;
    }

    private void observeViewModel() {
        mainViewModel.getRegistrationSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getActivity(), "Registrazione completata! Ora puoi accedere.", Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
