package com.example.myrecipes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Recipe> recipeList;
    private List<Recipe> allRecipes; // Полный список для поиска
    private Context context;

    public MyAdapter(ArrayList<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.allRecipes = new ArrayList<>(recipeList); // копия для поиска
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.title.setText(recipe.getTitle());
        holder.time.setText("Время: " + recipe.getPrepTime() + " мин");
        holder.servings.setText("Порции: " + recipe.getServings());

        if (recipe.getImagePath() != null) {
            File imgFile = new File(recipe.getImagePath());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imagePreview.setImageBitmap(myBitmap);
            } else {
                holder.imagePreview.setImageResource(R.drawable.placeholder);
            }
        } else {
            holder.imagePreview.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Метод фильтрации
    public void filter(String query) {
        query = query.toLowerCase().trim();
        recipeList.clear();

        if (query.isEmpty()) {
            recipeList.addAll(allRecipes);
        } else {
            for (Recipe recipe : allRecipes) {
                if (recipe.getTitle().toLowerCase().contains(query) ||
                        recipe.getIngredients().toLowerCase().contains(query)) {
                    recipeList.add(recipe);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, servings;
        ImageView imagePreview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            time = itemView.findViewById(R.id.textViewTime);
            servings = itemView.findViewById(R.id.textViewServings);
            imagePreview = itemView.findViewById(R.id.imageViewPreview);
        }
    }
}
