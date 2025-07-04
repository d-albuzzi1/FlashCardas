package com.example.flashcardas.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.ui.main.HomeFragment;

/**
 * ViewModel per gestire il Fragment attualmente visualizzato nell'app.
 *
 * - Tiene traccia del Fragment corrente tramite LiveData.
 * - Inizializza il Fragment con HomeFragment di default.
 * - Permette di cambiare dinamicamente il Fragment visualizzato dalla UI.
 */


public class MainViewModel extends ViewModel {

    private final MutableLiveData<Fragment> currentFragment = new MutableLiveData<>();

    public MainViewModel() {
        currentFragment.setValue(new HomeFragment());
    }

    public LiveData<Fragment> getCurrentFragment() {
        return currentFragment;
    }
    public void setCurrentFragment(Fragment fragment) {
        currentFragment.setValue(fragment);
    }
}
