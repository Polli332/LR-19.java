package com.example.holding.ui.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.holding.R;
import com.example.holding.databinding.FragmentHomeBinding;
import com.example.holding.ui.activity.MainActivity;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.newsButton.setOnClickListener(v->{
                change(new NewsFragment());
        });
        binding.vacanciesButton.setOnClickListener(v->{
                change(new VacanciesFragment());
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void change(Fragment f) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.home_Fragment_View, f)
                .addToBackStack(null) // Чтобы можно было вернуться назад
                .commit();
    }
}