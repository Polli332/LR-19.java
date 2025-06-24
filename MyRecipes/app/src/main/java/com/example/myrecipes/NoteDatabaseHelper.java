package com.example.myrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_INSTRUCTIONS = "instructions";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_PREP_TIME = "prep_time";
    public static final String COLUMN_SERVINGS = "servings";

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_INGREDIENTS + " TEXT," +
                COLUMN_INSTRUCTIONS + " TEXT," +
                COLUMN_IMAGE_PATH + " TEXT," +
                COLUMN_PREP_TIME + " TEXT," +
                COLUMN_SERVINGS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecipe(String title, String ingredients, String instructions, String imagePath, String prepTime, String servings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_INSTRUCTIONS, instructions);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_PREP_TIME, prepTime);
        values.put(COLUMN_SERVINGS, servings);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateRecipe(long id, String title, String ingredients, String instructions, String imagePath, String prepTime, String servings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_INSTRUCTIONS, instructions);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_PREP_TIME, prepTime);
        values.put(COLUMN_SERVINGS, servings);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        Recipe recipe = null;
        if (cursor != null && cursor.moveToFirst()) {
            recipe = new Recipe(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH)),
                    "", // category — не используется в таблице
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREP_TIME))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVINGS))),
                    false // isFavorite — не используется в таблице
            );
            cursor.close();
        }

        return recipe;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH)),
                        "", // category
                        Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREP_TIME))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVINGS))),
                        false // isFavorite
                );
                recipeList.add(recipe);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recipeList;
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
