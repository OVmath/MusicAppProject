package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicandroid.Models.SongObject;
import com.example.musicandroid.Models.UserModel;
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

import Adapter.MusicListAdapter;

public class LikedSongsActivity extends AppCompatActivity {
    Button local_song;
    BottomNavigationView bottomNavigationView;
    Button btn_playlist;
    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView tvHelloAcc;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    ArrayList<SongObject> listSong = new ArrayList<>(), listLikedSong = new ArrayList<>();
    MusicListAdapter adapter;
    String UID;
    ImageView AnhDaiDienMain;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    RecyclerView recyclerView;
    EditText editSearch;
    //end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_songs);

        //liem code
        editSearch = findViewById(R.id.edtSearch);
        tvHelloAcc = findViewById(R.id.tvHelloAcc);
        AnhDaiDienMain = findViewById(R.id.avatar);
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, signInOptions);
        recyclerView = findViewById(R.id.recyclerview_likedsongs);
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
                }
                else tvHelloAcc.setText(userModel.getTenHT());
                if (!userModel.getLinkAnh().equals("")){
                    Picasso.with(LikedSongsActivity.this).load(userModel.getLinkAnh()).into(AnhDaiDienMain);
                }

                listSong = userModel.getListSong();

                for (int i = 0; i < listSong.size(); i++){
                    if (listSong.get(i).getLiked()){
                        listLikedSong.add(listSong.get(i));
                    }
                }

                adapter = new MusicListAdapter(listLikedSong, LikedSongsActivity.this);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(LikedSongsActivity.this));
                recyclerView.setAdapter(adapter);

                ArrayList<SongObject> listSearch = new ArrayList<>();
                editSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().equals("")){
                            listSearch.clear();
                            for (int i = 0; i < listLikedSong.size(); i++){
                                if (listLikedSong.get(i).getNameSong().contains(editable.toString())){
                                    listSearch.add(listLikedSong.get(i));
                                }
                            }
                            adapter.clear();
                            adapter = new MusicListAdapter(listSearch, LikedSongsActivity.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);

                        }
                        else {
                            adapter.clear();
                            adapter = new MusicListAdapter(listLikedSong, LikedSongsActivity.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //liem end

        local_song = findViewById(R.id.btn_local_song);
        btn_playlist = findViewById(R.id.btnPlaylist);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);
        switchIntent();
    }

    private void switchIntent() {
        local_song.setOnClickListener(view -> {
            Intent intent = new Intent(LikedSongsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        btn_playlist.setOnClickListener(view -> {
            Intent intent = new Intent(LikedSongsActivity.this,PlaylistActivity.class);
            startActivity(intent);
            finish();
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(LikedSongsActivity.this, MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(LikedSongsActivity.this, Setting.class));
                return true;
            }
            else {
                startActivity(new Intent(LikedSongsActivity.this, MainActivity.class));
                return true;
            }
        });

    }
}