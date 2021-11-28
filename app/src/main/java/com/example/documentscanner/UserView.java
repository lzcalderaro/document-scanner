package com.example.documentscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserView extends AppCompatActivity {

    TextView name, data;
    Intent userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        name = findViewById(R.id.name);
        data = findViewById(R.id.data);

        userData = getIntent();

        name.setText("Name: " + userData.getExtras().getString("name"));
        data.setText(userData.getExtras().getString("data"));
    }
}