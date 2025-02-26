package com.example.flashcardas.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.ui.main.HomeFragment;

public class MainViewModel extends ViewModel {

    // LiveData per gestire il Fragment corrente
    private final MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();

    public MainViewModel() {
        // Imposta il Fragment di default, ad esempio FragmentHome
        currentFragment.setValue(new HomeFragment());
    }

    // Funzione per ottenere il Fragment corrente
    public LiveData<Fragment> getCurrentFragment() {
        return currentFragment;
    }

    // Funzione per cambiare il Fragment
    public void setCurrentFragment(Fragment fragment) {
        currentFragment.setValue(fragment);
    }
}

