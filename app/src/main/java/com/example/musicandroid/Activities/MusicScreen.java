package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import Adapter.ArtistRvAdapter;
import Adapter.TrendingRvAdapter;

public class MusicScreen extends AppCompatActivity {

    RecyclerView RvArtist, RvTrending, RvLateRelease;
    ArtistRvAdapter artistAdapter, lateReleaseAdapter;
    TrendingRvAdapter trendingAdapter;
    ArrayList<ArtistModels> listArtist, listLastestRelease;
    ArrayList<TrendingModels> listTrending;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        RvArtist = findViewById(R.id.rvArtist);
        RvTrending = findViewById(R.id.rvTrending);
        RvLateRelease = findViewById(R.id.rvLateRelease);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu);

        listArtist = new ArrayList<>();
        listTrending = new ArrayList<>();
        listLastestRelease = new ArrayList<>();

        listArtist.add(new ArtistModels("Ed Sheeran", R.drawable.ic_user_24 + ""));
        listArtist.add(new ArtistModels("Arijit Singh", R.drawable.ic_user_24 + ""));
        listArtist.add(new ArtistModels("Selena \n Gomez", R.drawable.ic_user_24 + ""));
        listArtist.add(new ArtistModels("Taylor Swift", R.drawable.ic_user_24 + ""));
        listArtist.add(new ArtistModels("Sonu Nigam",R.drawable.ic_user_24 + ""));
        listArtist.add(new ArtistModels("Shreya \n Ghoshal",R.drawable.ic_user_24 + ""));

        listTrending.add(new TrendingModels(R.drawable.trending + ""));
        listTrending.add(new TrendingModels(R.drawable.trending + ""));

        artistAdapter = new ArtistRvAdapter(listArtist);
        RvArtist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RvArtist.setAdapter(artistAdapter);

        trendingAdapter = new TrendingRvAdapter(listTrending);
        RvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RvTrending.setAdapter(trendingAdapter);

        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));
        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));
        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));

        lateReleaseAdapter = new ArtistRvAdapter(listLastestRelease);
        RvLateRelease.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RvLateRelease.setAdapter(lateReleaseAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_setting:
                        startActivity(new Intent(MusicScreen.this, Setting.class));
                }
                return true;
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