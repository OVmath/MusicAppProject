package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.Models.UserModels;
import com.example.musicandroid.R;
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
    ArtistRvAdapter artistAdapter, lateReleaseAdapter;
    TrendingRvAdapter trendingAdapter;
    ArrayList<ArtistModels> listArtist, listLastestRelease;
    ArrayList<TrendingModels> listTrending;
    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView tvHelloAcc;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModels userModels;
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
                        userModels = snapshot1.getValue(UserModels.class);
                    }
                }
                if (userModels.getTenHT().equals("")){
                    tvHelloAcc.setText("Vào setting để chỉnh tên hiển thị tên hiển thị");
                }
                else tvHelloAcc.setText(userModels.getTenHT());
                if (!userModels.getLinkAnh().equals("")){
                    Picasso.with(MusicScreen.this).load(userModels.getLinkAnh()).into(AnhDaiDienMain);
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