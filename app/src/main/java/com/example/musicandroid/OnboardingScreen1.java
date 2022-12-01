package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnboardingScreen1 extends AppCompatActivity {

    Button btnSkip, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen_1);
        btnNext = findViewById(R.id.btnNext1);
        btnSkip = findViewById(R.id.btnSkip1);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingScreen1.this, OnboardingScreen2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}