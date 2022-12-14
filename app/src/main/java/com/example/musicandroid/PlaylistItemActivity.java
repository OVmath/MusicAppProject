package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PlaylistItemActivity extends AppCompatActivity {
    ImageView btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_item);
        btn_exit = findViewById(R.id.img_exit);
        addEvents();
    }

    private void addEvents() {
        btn_exit.setOnClickListener(view -> onDestroy());
    }
}