package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnboardingScreen3 extends AppCompatActivity {

    Button btnNext03, btnSkip03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen_3);
        btnNext03 = findViewById(R.id.btnNext3);
        btnSkip03 = findViewById(R.id.btnSkip3);

        btnSkip03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OnboardingScreen3.class);
                startActivity(intent);
                finish();
            }
        });
    }
}