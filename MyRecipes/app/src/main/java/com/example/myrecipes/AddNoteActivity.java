package com.example.myrecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AddNoteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextTitle, editTextIngredients, editTextInstructions, editTextPrepTime, editTextServings;
    private Button buttonAddRecipe, buttonSelectImage;
    private ImageView imageViewPreview;
    private Uri selectedImageUri;

    private NoteDatabaseHelper dbHelper;
    private boolean isEdit = false;
    private int recipeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        editTextPrepTime = findViewById(R.id.editTextPrepTime);
        editTextServings = findViewById(R.id.editTextServings);
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        dbHelper = new NoteDatabaseHelper(this);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);

        if (isEdit) {
            recipeId = intent.getIntExtra("id", -1);
            editTextTitle.setText(intent.getStringExtra("title"));
            editTextIngredients.setText(intent.getStringExtra("ingredients"));
            editTextInstructions.setText(intent.getStringExtra("instructions"));

            int prepTime = intent.getIntExtra("prepTime", 0);
            int servings = intent.getIntExtra("servings", 1);
            editTextPrepTime.setText(String.valueOf(prepTime));
            editTextServings.setText(String.valueOf(servings));

            String imagePath = intent.getStringExtra("imagePath");
            if (!TextUtils.isEmpty(imagePath)) {
                selectedImageUri = Uri.parse(imagePath);
                imageViewPreview.setImageURI(selectedImageUri);
            }

            buttonAddRecipe.setText("Обновить рецепт");
        }

        buttonSelectImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickIntent, PICK_IMAGE_REQUEST);
        });

        buttonAddRecipe.setOnClickListener(v -> saveOrUpdateRecipe());
    }

    private void saveOrUpdateRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();
        String prepTime = editTextPrepTime.getText().toString().trim();
        String servings = editTextServings.getText().toString().trim();
        String imagePath = selectedImageUri != null ? new File(selectedImageUri.getPath()).getAbsolutePath() : null;

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(instructions)) {
            Toast.makeText(this, "Название и инструкция обязательны", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEdit && recipeId != -1) {
            dbHelper.updateRecipe(recipeId, title, ingredients, instructions, imagePath, prepTime, servings);
            Toast.makeText(this, "Рецепт обновлён", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.addRecipe(title, ingredients, instructions, imagePath, prepTime, servings);
            Toast.makeText(this, "Рецепт добавлен", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri sourceUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(sourceUri);
                File imageFile = new File(getFilesDir(), "image_" + System.currentTimeMillis() + ".jpg");
                OutputStream outputStream = new FileOutputStream(imageFile);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();

                selectedImageUri = Uri.fromFile(imageFile);
                imageViewPreview.setImageURI(selectedImageUri);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
