package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musicandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class thongBao extends AppCompatActivity {
    Button close;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_bao);
        close = findViewById(R.id.closePage);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);
        switchIntent();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(thongBao.this, Setting.class));
            }
        });
    }

    private void switchIntent() {


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(thongBao.this, MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(thongBao.this, Setting.class));
                return true;
            }
            else {
                startActivity(new Intent(thongBao.this, MainActivity.class));
                return true;
            }
        });

    }

}