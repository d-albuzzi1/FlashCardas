package com.example.flashcardas.repository;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Repository che gestisce l'autenticazione degli utenti tramite Firebase Authentication.
 * Fornisce metodi per registrazione, login, logout e verifica dello stato dell'utente.
 * Utilizza LiveData per notificare la ViewModel o la UI riguardo al successo o agli errori.
 */
public class AuthRepository {

    // Istanza di FirebaseAuth per gestire autenticazione
    private final FirebaseAuth auth;

    // LiveData per notificare la riuscita della registrazione
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();

    // LiveData per notificare la riuscita del login
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    // LiveData per eventuali messaggi di errore
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Costruttore: inizializza FirebaseAuth
    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Metodo per registrare un nuovo utente.
     * Controlla che i dati siano validi e poi tenta la registrazione con Firebase.
     */
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

        // Prova a creare l'utente
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationSuccess.setValue(true); // Registrazione ok
                    } else {
                        errorMessage.setValue(task.getException().getMessage()); // Errore da Firebase
                    }
                });
    }

    /**
     * Metodo per autenticare un utente esistente.
     */
    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginSuccess.setValue(true); // Login ok
                    } else {
                        errorMessage.setValue(task.getException().getMessage()); // Errore
                    }
                });
    }

    /**
     * Metodo per disconnettere l'utente attualmente loggato.
     */
    public void logout() {
        auth.signOut();
    }

    /**
     * Verifica se c'è un utente attualmente loggato.
     */
    public boolean isUserLoggedIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }

    // Getter per i LiveData che verranno osservati da ViewModel o UI
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
