package com.example.flashcardas.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcardas.ui.main.HomeFragment;

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
