package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LikedSongsActivity extends AppCompatActivity {
    Button local_song;
    Button btn_playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_songs);
        local_song = findViewById(R.id.btn_local_song);
        btn_playlist = findViewById(R.id.btnPlaylist);
        switchIntent();
    }

    private void switchIntent() {
        local_song.setOnClickListener(view -> {
            Intent intent = new Intent(LikedSongsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        btn_playlist.setOnClickListener(view -> {
            Intent intent = new Intent(LikedSongsActivity.this,PlaylistActivity.class);
            startActivity(intent);
            finish();
        });

    }

}