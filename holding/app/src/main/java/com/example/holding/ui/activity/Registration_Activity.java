package com.example.holding.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holding.R;
import com.example.holding.data.model.UserModel;
import com.example.holding.data.repository.DataBase;
import com.example.holding.data.repository.UserRepository;

public class Registration_Activity extends AppCompatActivity {

    private EditText editTextLogin, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        // Инициализация полей ввода
        editTextLogin = findViewById(R.id.editTextText);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword2);

        // Получаем экземпляр базы данных для записи и чтения
        DataBase database = new DataBase(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabaseInstance();

        // Создаем репозиторий пользователя
        UserRepository userRepository = new UserRepository(sqLiteDatabase);

        // Обработчик для кнопки регистрации/входа
        findViewById(R.id.button_gotovo_registration).setOnClickListener(view -> {
            String login = editTextLogin.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Создаем объект пользователя с введенными данными
            UserModel userModel = new UserModel(login, email, password);

            // Проверяем, есть ли такой пользователь в базе
            if (userRepository.isCorrect(login, email, password)) {
                // Если пользователь найден - авторизация успешна
                Toast.makeText(Registration_Activity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                // Перейдите на другой экран (например, главный экран)
                // startActivity(new Intent(Registration_Activity.this, MainActivity.class));
            } else {
                // Если пользователь не найден, то регистрируем нового
                userRepository.registerUser(userModel);
                Toast.makeText(Registration_Activity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                // Перейдите на другой экран (например, главный экран)
                // startActivity(new Intent(Registration_Activity.this, MainActivity.class));
            }
        });
    }

}
