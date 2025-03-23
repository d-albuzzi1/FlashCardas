package com.example.flashcardas.ui.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashcardas.R;
import com.example.flashcardas.ui.adapter.DeckAdapter;
import com.example.flashcardas.ui.welcome.WelcomeActivity;
import com.example.flashcardas.viewmodel.AuthViewModel;
import com.example.flashcardas.viewmodel.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private AuthViewModel authViewModel;

    private DeckAdapter deckAdapter;
    private ImageView accountImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        accountImage = view.findViewById(R.id.accountImage);
        accountImage.setOnClickListener(v -> showLogoutDialog());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDecks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        deckAdapter = new DeckAdapter(new ArrayList<>(), deckId -> {
            ManageCollectionsFragment manageFragment = ManageCollectionsFragment.newInstance(deckId);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, manageFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(deckAdapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getDecks().observe(getViewLifecycleOwner(), deckAdapter::setDecks);

        TextView emptyMessage = view.findViewById(R.id.emptyMessage);

        homeViewModel.getDecks().observe(getViewLifecycleOwner(), decks -> {
            if (decks != null && !decks.isEmpty()) {
                deckAdapter.setDecks(decks);
                emptyMessage.setVisibility(View.GONE);
            } else {
                emptyMessage.setVisibility(View.VISIBLE);
            }
        });



        return view;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Conferma Logout");
        builder.setMessage("Vuoi davvero disconnettere il tuo account?");

        builder.setPositiveButton("Disconnetti", (dialog, which) -> {
            performLogout();
        });

        builder.setNegativeButton("Ritorna alla home", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setCancelable(false); // Impedisce di chiudere toccando fuori dal popup
        builder.show();
    }

    private void performLogout() {

        authViewModel.logout();

        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
