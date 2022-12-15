package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.MenuItem;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.MainActivity;
import com.example.musicandroid.PlaylistActivity;
import com.example.musicandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Setting extends AppCompatActivity {

    //liem code

    BottomNavigationView bottomNavigationViewSetting;
    Button btnInfoUpdate;
    //end

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //liem code
        bottomNavigationViewSetting = findViewById(R.id.bottom_navi_menu_setting_activity);
        btnInfoUpdate = findViewById(R.id.btnInfoUpdate);

        btnInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, SuaInfoActivity.class));
            }
        });
        //end

        bottomNavigationView = findViewById(R.id.bottom_navi_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(Setting.this,MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_home){
                startActivity(new Intent(Setting.this,MainActivity.class));
                return true;
            }
            else {
                return true;
            }
        });

    }



}