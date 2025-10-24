package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get the city name from the intent
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city_name");

        // Display the city name
        TextView cityTextView = findViewById(R.id.text_city_name);
        cityTextView.setText(cityName);

        // Back button
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> { finish(); // Returns to MainActivity
        });
    }
}

