package com.example.holding.ui.activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.holding.R;
import com.example.holding.data.repository.DataBase;
import com.example.holding.ui.fragment.HomeFragment;
import com.example.holding.ui.fragment.MessageFragment;
import com.example.holding.ui.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private ImageView home;
    private ImageView profile;
    private ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        profile = findViewById(R.id.profile_button);
        home = findViewById(R.id.home_button);
        message = findViewById(R.id.message_Button);
        home.setOnClickListener(v ->
                change(new HomeFragment())
        );

        DataBase dataBase = new DataBase(getApplicationContext());
        SQLiteDatabase db = dataBase.getWritableDatabase();

        profile.setOnClickListener(v ->
                change(new ProfileFragment())
        );

        message.setOnClickListener(v ->
                change(new MessageFragment())
        );
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {


            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void change(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainerView, f);
        ft.commit();
    }

}