package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.R;

import java.util.ArrayList;

import Adapter.ArtistRvAdapter;

public class MusicScreen extends AppCompatActivity {

    RecyclerView RvArtist;
    ArtistRvAdapter artistAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ArtistModels> listArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        RvArtist = findViewById(R.id.rvArtist);

        listArtist = new ArrayList<>();

        listArtist.add(new ArtistModels("Ed Sheeran"));
        listArtist.add(new ArtistModels("Arijit Singh"));
        listArtist.add(new ArtistModels("Selena \n Gomez"));
        listArtist.add(new ArtistModels("Taylor Swift"));
        listArtist.add(new ArtistModels("Sonu Nigam"));
        listArtist.add(new ArtistModels("Shreya \n Ghoshal"));

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        artistAdapter = new ArtistRvAdapter(listArtist);
        RvArtist.setLayoutManager(linearLayoutManager);
        RvArtist.setAdapter(artistAdapter);


    }

}