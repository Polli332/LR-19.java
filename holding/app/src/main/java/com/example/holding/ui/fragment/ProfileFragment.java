package com.example.holding.ui.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holding.R;
import com.example.holding.databinding.FragmentHomeBinding;
import com.example.holding.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.registrationButton.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "active", LENGTH_SHORT).show();
            change(new RegistrationFragment());
        });

        return binding.getRoot();
    }

    private void change(Fragment f) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, f)
                //.addToBackStack(null) // Позволяет вернуться назад
                .commit();

    }
}