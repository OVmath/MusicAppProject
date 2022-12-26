package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.Models.SongObject;
import com.example.musicandroid.Models.UserModel;
import com.example.musicandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import Adapter.MusicListAdapter;

public class MainActivity extends AppCompatActivity implements Serializable {
    RecyclerView recyclerView;
    ImageView btnAddMusic;
    Button btnPlaylist;
    Button btnLikedSongs;

    BottomNavigationView bottomNavigationView;
    ArrayList<SongObject> songsList = new ArrayList<>(), songListSearch = new ArrayList<>();
    MusicListAdapter adapter;

    DatabaseReference databaseReference =
            FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Songs");
    //liem code
    EditText edtSearch;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView tvHelloAcc;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    String UID, keyUser;
    ImageView AnhDaiDienMain;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
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

        //liem code
        edtSearch = findViewById(R.id.edtSearch);

        AnhDaiDienMain = findViewById(R.id.avatar);
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
                        keyUser = snapshot1.getKey();
                    }
                }
                if (userModel.getTenHT().equals("")){
                    tvHelloAcc.setText("Vào setting để chỉnh tên hiển thị");
                }
                else tvHelloAcc.setText(userModel.getTenHT());
                if (!userModel.getLinkAnh().equals("")){
                    Picasso.with(MainActivity.this).load(userModel.getLinkAnh()).into(AnhDaiDienMain);
                }
                if (userModel.getListSong() == null){
                    Toast.makeText(MainActivity.this, "Không có bài hát nào trong tài khoản", Toast.LENGTH_LONG).show();
                }
                else{
                    songsList = userModel.getListSong();
                    adapter = new MusicListAdapter(songsList,MainActivity.this);
                    adapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                }


                edtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().equals("")){
                            songListSearch.clear();
                            for (int i = 0; i < songsList.size(); i++){
                                if (songsList.get(i).getNameSong().contains(editable.toString())){
                                    songListSearch.add(songsList.get(i));
                                }
                            }
                            adapter.clear();
                            adapter = new MusicListAdapter(songListSearch, MainActivity.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);

                        }
                        else {
                            adapter.clear();
                            adapter = new MusicListAdapter(songsList, MainActivity.this);
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

    public void showDialogMenu(SongObject songObject, int position) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.dialog_menu);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btn_edit   = dialog.findViewById(R.id.btn_edit);
        Button btn_delete = dialog.findViewById(R.id.btn_delete);
        Button exit = dialog.findViewById(R.id.btn_exit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("Object", songObject);
                startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xóa bài hát");
                builder.setMessage("Bạn có chắc muốn xóa bài hát không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        songsList.remove(songObject);
                        userModel.setListSong(songsList);
                        database.child(keyUser).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Xóa nhạc thành công", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        });
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
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