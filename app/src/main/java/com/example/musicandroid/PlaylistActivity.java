package com.example.musicandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.Activities.MusicScreen;
import com.example.musicandroid.Activities.Setting;
import com.example.musicandroid.Models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class PlaylistActivity extends AppCompatActivity {
    ImageView addPlaylist;
    ArrayList<PlaylistObject> playlistsList = new ArrayList<>();
    EditText edtPlaylistName;
    RecyclerView recyclerViewPlaylist;
    Button btnSave;
    Button btnDestroy;
    Button btnLocalSong;
    Button btnLikedSong;
    Button btn_choose;
    ImageView img_chosen;
    PlaylistAdapter adapter;
    BottomNavigationView bottomNavigationView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Playlist");
    private Uri imgUri;

    //liem code
    EditText edtSearch;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    TextView tvHelloAcc;
    ArrayList<PlaylistObject> playListSearch = new ArrayList<>();
    PlaylistObject playlistObject = new PlaylistObject();
    String UID, keyUser;
    ImageView AnhDaiDienMain;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    StorageReference referenceAnhPlayList;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //liem code
        edtSearch = findViewById(R.id.edtSearch);
        tvHelloAcc = findViewById(R.id.tvHelloAcc);
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
                    Picasso.with(PlaylistActivity.this).load(userModel.getLinkAnh()).into(AnhDaiDienMain);
                }
                if (userModel.getListPlayList().isEmpty()){
                    Toast.makeText(PlaylistActivity.this, "Không có play list nào trong tài khoản", Toast.LENGTH_LONG).show();
                }
                else{
                    playlistsList = userModel.getListPlayList();
                    adapter = new PlaylistAdapter(playlistsList ,PlaylistActivity.this);
                    adapter.notifyDataSetChanged();
                    recyclerViewPlaylist.setLayoutManager(new GridLayoutManager(PlaylistActivity.this, 2));
                    recyclerViewPlaylist.setAdapter(adapter);
                }

                ArrayList<PlaylistObject> list = playlistsList;
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
                            playListSearch.clear();
                            for (int i = 0; i < list.size(); i++){
                                if (list.get(i).getNamePlaylist().contains(editable.toString())){
                                    playListSearch.add(list.get(i));
                                }
                            }
                            adapter.clear();
                            adapter = new PlaylistAdapter(playListSearch, PlaylistActivity.this);
                            adapter.notifyDataSetChanged();
                            recyclerViewPlaylist.setAdapter(adapter);

                        }
                        else {
                            adapter.clear();
                            adapter = new PlaylistAdapter(list, PlaylistActivity.this);
                            adapter.notifyDataSetChanged();
                            recyclerViewPlaylist.setAdapter(adapter);
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //liem end

        addPlaylist = findViewById(R.id.imgAddPlalist);
        addPlaylistEven();
        btnLocalSong = findViewById(R.id.btn_local_song);
        btnLikedSong = findViewById(R.id.btnLikedSongs);
        recyclerViewPlaylist = findViewById(R.id.recyclerview_playlist);
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_main_activity);
        switchIntent();
    }

    private void switchIntent() {
        btnLikedSong.setOnClickListener(view -> {
            Intent intent = new Intent(PlaylistActivity.this,LikedSongsActivity.class);
            startActivity(intent);
        });

        btnLocalSong.setOnClickListener(view -> {
            Intent intent = new Intent(PlaylistActivity.this,MainActivity.class);
            startActivity(intent);
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(PlaylistActivity.this, MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_setting){
                startActivity(new Intent(PlaylistActivity.this, Setting.class));
                return true;
            }
            else {
                startActivity(new Intent(PlaylistActivity.this, MainActivity.class));
                return true;
            }
        });
    }

    private void addPlaylistEven() {
        addPlaylist.setOnClickListener(view -> showDialogAddPlaylist());

    }

    public void showDialogAddPlaylist(){
        Dialog dialog = new Dialog(PlaylistActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.dialog_add_playlist);
        edtPlaylistName = dialog.findViewById(R.id.edtName);
        btnSave = dialog.findViewById(R.id.btn_save);
        btnDestroy = dialog.findViewById(R.id.btn_destroy);
        btn_choose = dialog.findViewById(R.id.btn_choose_img);
        img_chosen = dialog.findViewById(R.id.ChoseImg);

        dialog.show();
        btnSave.setOnClickListener(view -> {
            playlistObject.setNamePlaylist(edtPlaylistName.getText().toString());
//            playlistsList.add(playlistObject);
            UpAnhPlayListLenFirebase();
            dialog.dismiss();
//            adapter.notifyDataSetChanged();
        });


        btn_choose.setOnClickListener(view -> {
            Intent imgIntent = new Intent();
            imgIntent.setType("image/*");
            imgIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imgIntent,"Select Picture"),113);
        });
        btnDestroy.setOnClickListener(view -> dialog.dismiss());
    }

    public void UpAnhPlayListLenFirebase(){

        ProgressDialog dialog2 = new ProgressDialog(PlaylistActivity.this);
        dialog2.show();

        referenceAnhPlayList = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                .getReference("AnhPlayList").child(imgUri.getLastPathSegment());

        referenceAnhPlayList.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                imgUri = task.getResult();
                playlistObject.setImgPath(imgUri.toString());
                playlistObject.setKeyPlaylist(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                userModel.getListPlayList().add(playlistObject);

                dialog2.dismiss();

                database.child(keyUser).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(PlaylistActivity.this, "Thêm nhạc thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                int CurProgress = (int) progress;
                dialog2.setMessage("Upload: " + CurProgress + "%");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113&&resultCode==RESULT_OK)
        {
            try {
                assert data != null;
                imgUri = data.getData();
                Bitmap img = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        imgUri
                );
                img_chosen.setImageBitmap(img);
                playlistObject.setImgPath(String.valueOf(imgUri));
//                databaseHelper.QueryData("UPDATE Product2 SET Img = '"+imgUri+"'"+"WHERE Id = "+p);
            }
            catch (Exception e)
            {
                Toast.makeText(PlaylistActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                Log.e("ERROR",e.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerViewPlaylist!=null){
            recyclerViewPlaylist.setAdapter(new PlaylistAdapter(playlistsList,PlaylistActivity.this));
        }
    }

    public void showDialogMenu(PlaylistObject playlistData) {
        Dialog dialog = new Dialog(PlaylistActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.dialog_menu);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btn_edit   = dialog.findViewById(R.id.btn_edit);
        Button btn_delete = dialog.findViewById(R.id.btn_delete);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaylistActivity.this);
                builder.setTitle("Xóa bài hát");
                builder.setMessage("Bạn có chắc muốn xóa bài hát không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        playlistsList.remove(playlistData);
                        userModel.setListPlayList(playlistsList);
                        database.child(keyUser).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PlaylistActivity.this, "Xóa nhạc thành công", Toast.LENGTH_SHORT).show();
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

        Button exit = dialog.findViewById(R.id.btn_exit);
        exit.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}