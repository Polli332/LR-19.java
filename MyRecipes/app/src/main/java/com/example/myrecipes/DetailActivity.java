package com.example.myrecipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    private TextView textTitle, textIngredients, textInstructions, textPrepTime, textServings;
    private ImageView imageRecipe;
    private Button btnDelete, btnEdit;
    private NoteDatabaseHelper dbHelper;
    private int recipeId;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        // Инициализация компонентов
        textTitle = findViewById(R.id.textTitle);
        textIngredients = findViewById(R.id.textIngredients);
        textInstructions = findViewById(R.id.textInstructions);
        textPrepTime = findViewById(R.id.textPrepTime);
        textServings = findViewById(R.id.textServings);
        imageRecipe = findViewById(R.id.imageRecipe);
        btnDelete = findViewById(R.id.buttonDelete);
        btnEdit = findViewById(R.id.buttonEdit);

        dbHelper = new NoteDatabaseHelper(this);

        // Получение ID рецепта
        recipeId = getIntent().getIntExtra("recipe_id", -1);


        if (recipeId != -1) {
            recipe = dbHelper.getRecipe((int) recipeId);


            if (recipe != null) {
                // Заполнение UI
                textTitle.setText(recipe.getTitle());
                String[] items = recipe.getIngredients().split("\n");
                StringBuilder formatted = new StringBuilder();
                for (String item : items) {
                    formatted.append("• ").append(item.trim()).append("\n");
                }
                textIngredients.setText(formatted.toString());

                textInstructions.setText(recipe.getInstructions());
                textPrepTime.setText("Время приготовления: " + recipe.getPrepTime() + " мин");
                textServings.setText("Порций: " + recipe.getServings());

                if (recipe.getImagePath() != null) {
                    File imgFile = new File(recipe.getImagePath());
                    if (imgFile.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageRecipe.setImageBitmap(bitmap);
                    } else {
                        imageRecipe.setImageResource(R.drawable.placeholder);
                    }
                } else {
                    imageRecipe.setImageResource(R.drawable.placeholder);
                }



                // Удаление рецепта
                btnDelete.setOnClickListener(v -> {
                    dbHelper.deleteNote(recipeId);
                    Toast.makeText(this, "Рецепт удалён", Toast.LENGTH_SHORT).show();
                    finish();
                });

                // Редактирование рецепта
                btnEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(DetailActivity.this, AddNoteActivity.class);
                    intent.putExtra("isEdit", true);
                    intent.putExtra("id", recipe.getId());
                    intent.putExtra("title", recipe.getTitle());
                    intent.putExtra("ingredients", recipe.getIngredients());
                    intent.putExtra("instructions", recipe.getInstructions());
                    intent.putExtra("imagePath", recipe.getImagePath());
                    intent.putExtra("prepTime", recipe.getPrepTime());
                    intent.putExtra("servings", recipe.getServings());
                    startActivity(intent);
                    finish(); // Закрываем DetailActivity после перехода к редактированию
                });

            } else {
                Toast.makeText(this, "Рецепт не найден", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            Toast.makeText(this, "Ошибка: ID рецепта не получен", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
