package com.example.holding.data.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.holding.data.model.UserModel;

public class UserRepository {
    private SQLiteDatabase database;
    public UserRepository(SQLiteDatabase database) {
        this.database = database;
    }

    // Проверка правильности логина, email и пароля
    public boolean isCorrect(String login, String email, String password) {
        // SQL-запрос для проверки логина, email и пароля
        String query = "SELECT * FROM users WHERE login = ? AND email = ? AND password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{login, email, password});

        // Если пользователь найден, возвращаем true
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }

        // Если пользователь не найден
        if (cursor != null) {
            cursor.close();
        }

        return false;
    }

    public void registerUser(UserModel user) {
        String insertQuery = "INSERT INTO users (login, email, password) VALUES (?, ?, ?)";
        database.execSQL(insertQuery, new Object[]{user.getLogin(), user.getEmail(), user.getPassword()});
        Log.d("DB", "User inserted: " + user.getLogin());
    }

}
