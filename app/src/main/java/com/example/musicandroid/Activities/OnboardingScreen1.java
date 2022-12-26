package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musicandroid.R;
import com.example.musicandroid.Models.SongObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OnboardingScreen1 extends AppCompatActivity {

    Button btnSkip, btnNext;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("lastestRelease");
    SongObject songObject = new SongObject();
    ArrayList<SongObject> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen_1);
        btnNext = findViewById(R.id.btnNext1);
        btnSkip = findViewById(R.id.btnSkip1);

        /*for (int i = 1; i <= 3; i++){
            songObject.setKeySong(String.valueOf(Calendar.getInstance().getTimeInMillis()));
            songObject.setImgSong("0");
            songObject.setNameSong("0");
            songObject.setLinkSong("0");
            list.add(songObject);
        }
        database.setValue(list);*/

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);

    }

}