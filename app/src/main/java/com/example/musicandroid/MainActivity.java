package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        database.child("test").setValue("test 001");

    }
}