package com.example.musicandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.aware.PeerHandle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.util.List;



public class AddSongActivity extends AppCompatActivity {
    Button btn_choose;
    EditText edtTenBH, edtArtist;
    Button btnChonBH, btnThemLoi, btnLuu;
    ImageView img_chosen;
    Button btn_exit;
    String link_song;
    SongObject songObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        songObject = new SongObject();
        btn_choose = findViewById(R.id.btn_image);
        img_chosen = findViewById(R.id.image);
        btn_exit   = findViewById(R.id.btn_exit);
        btnChonBH = findViewById(R.id.choose_song);
        btnThemLoi = findViewById(R.id.btn_add_lyrics);
        edtTenBH = findViewById(R.id.edtName);
        edtArtist = findViewById(R.id.edtArtist);
        btnLuu = findViewById(R.id.btn_save);
        ActivityResultLauncher<String> imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        img_chosen.setImageURI(result);
                        Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        btn_choose.setOnClickListener(view -> imageResultLauncher.launch("image/*"));
        //Chọn nhạc
        ActivityResultLauncher<String> audioResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        link_song = result.toString();
                        if (new File(link_song).exists()){

                        }
                        Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        btnChonBH.setOnClickListener(view -> audioResultLauncher.launch("audio/*"));
        //Exit
        btn_exit.setOnClickListener(view -> {
//            Intent intent = new Intent(AddSongActivity.this,MainActivity.class);
//            startActivity(intent);
            finish();

        });

    }
}