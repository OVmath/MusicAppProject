package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.musicandroid.Activities.MusicScreen;
import com.example.musicandroid.Activities.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LikedSongsActivity extends AppCompatActivity {
    Button local_song;
    BottomNavigationView bottomNavigationView;
    Button btn_playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_songs);
        local_song = findViewById(R.id.btn_local_song);
        btn_playlist = findViewById(R.id.btnPlaylist);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);
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

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(LikedSongsActivity.this, MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(LikedSongsActivity.this, Setting.class));
                return true;
            }
            else {
                return true;
            }
        });

    }

}