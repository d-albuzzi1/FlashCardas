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

public class RegisterFragment extends Fragment {
    private AuthViewModel authViewModel;
    private EditText emailInput, passwordInput, confirmPasswordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput);
        Button registerButton = view.findViewById(R.id.registerButton);
        Button backButton = view.findViewById(R.id.backButton);

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();
            authViewModel.registerUser(email, password, confirmPassword);
        });

        backButton.setOnClickListener(v -> loadLoginFragment());


        observeViewModel();

        return view;
    }

    private void observeViewModel() {
        authViewModel.getRegistrationSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getActivity(), "Registrazione completata!", Toast.LENGTH_SHORT).show();
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

    private void loadLoginFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }


}
