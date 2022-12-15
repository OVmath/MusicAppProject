package com.example.musicandroid;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Playlist");
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        addPlaylist = findViewById(R.id.imgAddPlalist);
        addPlaylistEven();
        btnLocalSong = findViewById(R.id.btn_local_song);
        btnLikedSong = findViewById(R.id.btnLikedSongs);
        recyclerViewPlaylist = findViewById(R.id.recyclerview_playlist);
        switchIntent();
        adapter = new PlaylistAdapter(playlistsList,this);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playlistsList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    PlaylistObject playlistObject = snapshot1.getValue(PlaylistObject.class);
                    playlistsList.add(playlistObject);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerViewPlaylist.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewPlaylist.setAdapter(adapter);
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
            PlaylistObject playlistObject = new PlaylistObject();
            playlistObject.setNamePlaylist(edtPlaylistName.getText().toString());
//            playlistsList.add(playlistObject);
            databaseReference.push().setValue(playlistObject).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(PlaylistActivity.this, "Thêm play list thành công", Toast.LENGTH_SHORT).show();
                }
            });
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
        Button exit = dialog.findViewById(R.id.btn_exit);
        exit.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}