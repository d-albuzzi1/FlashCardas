package com.example.flashcardas.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.ui.welcome.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeViewModel extends ViewModel {

    private final MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();
    private final FirebaseAuth auth;
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();


    public WelcomeViewModel() {
        // Imposta il Fragment di default
        currentFragment.setValue(new LoginFragment());

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
                        loginSuccess.setValue(true);  // Imposta il successo del login
                    } else {
                        errorMessage.setValue(task.getException().getMessage());  // Imposta l'errore
                    }
                });
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }
    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}

