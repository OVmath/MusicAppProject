package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import Adapter.ArtistRvAdapter;
import Adapter.TrendingRvAdapter;

public class MusicScreen extends AppCompatActivity {

    RecyclerView RvArtist, RvTrending, RvLateRelease;
    ArtistRvAdapter artistAdapter, lateReleaseAdapter;
    TrendingRvAdapter trendingAdapter;
    ArrayList<ArtistModels> listArtist, listLastestRelease;
    ArrayList<TrendingModels> listTrending;

    TextView tvHelloAcc;
    //liem code start
    FirebaseAuth auth = FirebaseAuth.getInstance();
    BottomNavigationView bottomNavigationView;
    //liem end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        //Liem code start
        tvHelloAcc = findViewById(R.id.tvHelloAccMusicScreen);
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            Toast.makeText(this, "GG", Toast.LENGTH_SHORT).show();
            tvHelloAcc.setText("Hello " + GoogleSignIn.getLastSignedInAccount(this).getDisplayName());
        }
        else if (auth.getCurrentUser() != null){
            tvHelloAcc.setText("Hello " + auth.getCurrentUser().getEmail());
        }
        else {
            startActivity(new Intent(getApplicationContext(), OnboardingScreen1.class));
        }
        /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                        try {
                            Toast.makeText(MainActivity.this, "Facebook", Toast.LENGTH_SHORT).show();
                            tvHelloAcc.setText("Hello " + jsonObject.getString("name"));
                        }
                        catch (Exception ex) {
                            Toast.makeText(MainActivity.this, ex + "", Toast.LENGTH_SHORT).show();
                        }
                    }
        });*/
        //end

        RvArtist = findViewById(R.id.rvArtist);
        RvTrending = findViewById(R.id.rvTrending);
        RvLateRelease = findViewById(R.id.rvLateRelease);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);

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
        //liem end

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_home){
                startActivity(new Intent(MusicScreen.this,MainActivity.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(MusicScreen.this,Setting.class));
                return true;
            }
            else {
                return true;
            }
//            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}