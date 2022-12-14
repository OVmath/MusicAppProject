package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.R;

import java.util.ArrayList;

import Adapter.ArtistRvAdapter;
import Adapter.TrendingRvAdapter;

public class MusicScreen extends AppCompatActivity {

    RecyclerView RvArtist, RvTrending, RvLateRelease;
    ArtistRvAdapter artistAdapter, lateReleaseAdapter;
    TrendingRvAdapter trendingAdapter;
    ArrayList<ArtistModels> listArtist, listLastestRelease;
    ArrayList<TrendingModels> listTrending;
    ImageView settingBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        RvArtist = findViewById(R.id.rvArtist);
        RvTrending = findViewById(R.id.rvTrending);
        RvLateRelease = findViewById(R.id.rvLateRelease);
        settingBottomNav = findViewById(R.id.imgSettingMusicScreen);

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

        settingBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MusicScreen.this, Setting.class));
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