package com.example.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    NoteDatabaseHelper dbHelper;
    Button btnAddRecipe;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddRecipe = findViewById(R.id.add_button);
        searchView = findViewById(R.id.searchView); //поиск

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRecipes();

        btnAddRecipe.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class)));

        //поиск
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null) adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) adapter.filter(newText);
                return true;
            }
        });
    }

    private void loadRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>(dbHelper.getAllRecipes());
        adapter = new MyAdapter(recipes, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes(); //Обновление списка
    }
}
