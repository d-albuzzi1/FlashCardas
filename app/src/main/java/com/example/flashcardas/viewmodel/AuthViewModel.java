package com.example.flashcardas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.flashcardas.repository.AuthRepository;

/**
 * ViewModel per gestire l'autenticazione dell'utente.
 *
 * - Espone metodi per registrazione, login e logout.
 * - Fornisce LiveData per osservare lo stato di successo o errori.
 * - Funziona come intermediario tra UI e AuthRepository.
 */

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository(); // Hilt
    }

    public void registerUser(String email, String password, String confirmPassword) {
        authRepository.registerUser(email, password, confirmPassword);
    }

    public void loginUser(String email, String password) {
        authRepository.loginUser(email, password);
    }

    public void logout() {
        authRepository.logout();
    }

    public boolean isUserLoggedIn() {
        return authRepository.isUserLoggedIn();
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return authRepository.getRegistrationSuccess();
    }

    public LiveData<Boolean> getLoginSuccess() {
        return authRepository.getLoginSuccess();
    }

    public LiveData<String> getErrorMessage() {
        return authRepository.getErrorMessage();
    }
}
