package com.example.lr_19java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] students = {"Яцевич", "Рязанова", "Шпак", "Рыжук", "Сухобоевский"};
    int currentIndex = 0;  // начальный студент (выполняющий лабу)

    TextView previousStudent;
    TextView currentStudent;
    TextView nextStudent;
    Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousStudent = findViewById(R.id.previousStudent);
        currentStudent = findViewById(R.id.currentStudent);
        nextStudent = findViewById(R.id.nextStudent);
        showButton = findViewById(R.id.showButton);

        updateStudentViews();

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переключаемся на следующего студента
                currentIndex = (currentIndex + 1) % students.length;
                updateStudentViews();
            }
        });
    }

    private void updateStudentViews() {
        int prevIndex = (currentIndex - 1 + students.length) % students.length;
        int nextIndex = (currentIndex + 1) % students.length;

        previousStudent.setText("Предыдущий: " + students[prevIndex]);
        currentStudent.setText("Текущий: " + students[currentIndex]);
        nextStudent.setText("Следующий: " + students[nextIndex]);
    }
}
