package com.example.musicandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.Activities.MusicScreen;
import com.example.musicandroid.Activities.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.musicandroid.Activities.MusicScreen;
import com.example.musicandroid.Activities.OnboardingScreen1;
import com.example.musicandroid.Activities.Setting;
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

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView btnAddMusic;
    Button btnPlaylist;
    Button btnLikedSongs;
    TextView tvHelloAcc;

    BottomNavigationView bottomNavigationView;
    ArrayList<SongObject> songsList = new ArrayList<>();
    MusicListAdapter adapter;

    DatabaseReference databaseReference =
            FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Songs");
    //liem code start
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_music);
        btnAddMusic = findViewById(R.id.imgAdd);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnLikedSongs = findViewById(R.id.btnLikedSongs);
        tvHelloAcc = findViewById(R.id.tvHelloAcc);

        //Liem code start

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, signInOptions);
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            Toast.makeText(this, GoogleSignIn.getLastSignedInAccount(this).getId(), Toast.LENGTH_SHORT).show();
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

        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);
        switchIntent();
        if(!checkPermission()){
            requestPermission();
            return;
        }
//        String[] projection = {
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.DURATION
//        };

//        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";
//        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
//        while(cursor.moveToNext()){
//            SongObject songData = new SongObject(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//            if(new File(songData.getLinkSong()).exists())
//                songsList.add(songData);
////                adapter.notifyDataSetChanged();
//
//        }
        adapter = new MusicListAdapter(songsList,MainActivity.this);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    SongObject songObject = snapshot1.getValue(SongObject.class);
                    songsList.add(songObject);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Errror",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void switchIntent(){
        btnPlaylist.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,PlaylistActivity.class);
            startActivity(intent);
            finish();
        });

        btnLikedSongs.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,LikedSongsActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddMusic.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddSongActivity.class);
            startActivity(intent);
//            finish();
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(MainActivity.this,MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(MainActivity.this,Setting.class));
                return true;
            }
            else {
                return true;
            }
        });
    }

    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTTINGS",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView!=null){
            recyclerView.setAdapter(new MusicListAdapter(songsList,MainActivity.this));
        }
    }

    public void showDialogMenu(SongObject songObject) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.dialog_menu);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btn_edit   = dialog.findViewById(R.id.btn_edit);
        Button btn_delete = dialog.findViewById(R.id.btn_delete);
        Button exit = dialog.findViewById(R.id.btn_exit);
        exit.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    //liem code start
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gsc.signOut();
        auth.signOut();
        System.exit(0);

    }
    //end
}