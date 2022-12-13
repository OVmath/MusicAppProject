package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    ArrayList<ArtistModels> listArtist, listLastestRelease;
    ArrayList<TrendingModels> listTrending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        RvArtist = findViewById(R.id.rvArtist);
        RvTrending = findViewById(R.id.rvTrending);
        RvLateRelease = findViewById(R.id.rvLateRelease);

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

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        artistAdapter = new ArtistRvAdapter(listArtist);
        RvArtist.setLayoutManager(linearLayoutManager);
        RvArtist.setAdapter(artistAdapter);

        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trendingAdapter = new TrendingRvAdapter(listTrending);
        RvTrending.setLayoutManager(linearLayoutManager2);
        RvTrending.setAdapter(trendingAdapter);

        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));
        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));
        listLastestRelease.add(new ArtistModels("Song name", R.drawable.latest_release + ""));
        linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lateReleaseAdapter = new ArtistRvAdapter(listLastestRelease);
        RvLateRelease.setLayoutManager(linearLayoutManager3);
        RvLateRelease.setAdapter(lateReleaseAdapter);


    }

}