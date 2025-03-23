package com.example.flashcardas.repository;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private final FirebaseAuth auth;
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessage.setValue("Inserisci tutti i dati");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessage.setValue("Le password non corrispondono");
            return;
        }

        if (password.length() < 6) {
            errorMessage.setValue("La password deve avere almeno 6 caratteri");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationSuccess.setValue(true);
                    } else {
                        errorMessage.setValue(task.getException().getMessage());
                    }
                });
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginSuccess.setValue(true);
                    } else {
                        errorMessage.setValue(task.getException().getMessage());
                    }
                });
    }

    public void logout() {
        auth.signOut();
    }

    public boolean isUserLoggedIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }

    public MutableLiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
