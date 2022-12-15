package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Setting extends AppCompatActivity {

    //liem code

    BottomNavigationView bottomNavigationViewSetting;
    Button btnInfoUpdate;
    //end

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

    }



}