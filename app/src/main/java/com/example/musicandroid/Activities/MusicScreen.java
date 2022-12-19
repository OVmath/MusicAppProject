package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.Models.UserModel;
import com.example.musicandroid.PlaylistAdapter;
import com.example.musicandroid.PlaylistObject;
import com.example.musicandroid.R;
import com.example.musicandroid.SongObject;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Adapter.ArtistRvAdapter;
import Adapter.TrendingRvAdapter;

public class MusicScreen extends AppCompatActivity {

    RecyclerView RvArtist, RvTrending, RvLateRelease;
    PlaylistAdapter artistAdapter;
    ArtistRvAdapter lateReleaseAdapter;
    TrendingRvAdapter trendingAdapter;
    ArrayList<PlaylistObject> listArtist;
    ArrayList<ArtistModels> listLastestRelease;
    ArrayList<SongObject> listTrending;
    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView tvHelloAcc;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    String UID;
    ImageView AnhDaiDienMain;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    //end
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);

        //liem code
        tvHelloAcc = findViewById(R.id.tvHelloAccMusicScreen);
        AnhDaiDienMain = findViewById(R.id.imgAnhMusic);
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, signInOptions);
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            UID = GoogleSignIn.getLastSignedInAccount(this).getId();
        }
        else if (auth.getCurrentUser() != null){
            UID = auth.getCurrentUser().getUid();
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

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if (UID.equals(snapshot1.child("uid").getValue().toString())){
                        userModel = snapshot1.getValue(UserModel.class);
                    }
                }
                if (userModel.getTenHT().equals("")){
                    tvHelloAcc.setText("Vào setting để chỉnh tên hiển thị");
                    tvHelloAcc.setTextSize(20);
                }
                else tvHelloAcc.setText(userModel.getTenHT());
                if (!userModel.getLinkAnh().equals("")){
                    Picasso.with(MusicScreen.this).load(userModel.getLinkAnh()).into(AnhDaiDienMain);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //liem end

        RvArtist = findViewById(R.id.rvArtist);
        RvTrending = findViewById(R.id.rvTrending);
        RvLateRelease = findViewById(R.id.rvLateRelease);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);

        listArtist = new ArrayList<>();
        listTrending = new ArrayList<>();
        listLastestRelease = new ArrayList<>();

        DatabaseReference databasePlayList = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("listPlayList");

        databasePlayList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                PlaylistObject playlistObject = new PlaylistObject();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    playlistObject = snapshot1.getValue(PlaylistObject.class);
                    listArtist.add(playlistObject);
                }

                artistAdapter = new PlaylistAdapter(listArtist, MusicScreen.this);
                RvArtist.setLayoutManager(new LinearLayoutManager(MusicScreen.this, LinearLayoutManager.HORIZONTAL, false));
                RvArtist.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference trendingDatabase = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("listTrending");

        trendingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SongObject songObject = new SongObject();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    songObject = snapshot1.getValue(SongObject.class);
                    listTrending.add(songObject);
                }

                trendingAdapter = new TrendingRvAdapter(listTrending, MusicScreen.this);
                RvTrending.setLayoutManager(new LinearLayoutManager(MusicScreen.this, LinearLayoutManager.HORIZONTAL, false));
                RvTrending.setAdapter(trendingAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                startActivity(new Intent(MusicScreen.this, MainActivity.class));
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