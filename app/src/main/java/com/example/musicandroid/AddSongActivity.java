package com.example.musicandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.aware.PeerHandle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.musicandroid.Activities.SuaInfoActivity;
import com.example.musicandroid.Models.UserModels;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class AddSongActivity extends AppCompatActivity {
    Button btn_choose;
    EditText edtTenBH, edtArtist;
    Button btnChonBH, btnThemLoi, btnLuu;
    ImageView img_chosen;
    Button btn_exit;
    String link_song;
    SongObject songObject;
    //liem code start
    FirebaseAuth auth = FirebaseAuth.getInstance();
    UserModels userModels;
    String UID;
    Uri uriAnh, uriNhac;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    StorageReference reference;
    String key;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        songObject = new SongObject();
        btn_choose = findViewById(R.id.btn_image);
        img_chosen = findViewById(R.id.image);
        btn_exit   = findViewById(R.id.btn_exit);
        btnChonBH = findViewById(R.id.choose_song);
        btnThemLoi = findViewById(R.id.btn_add_lyrics);
        edtTenBH = findViewById(R.id.edtName);
        edtArtist = findViewById(R.id.edtArtist);
        btnLuu = findViewById(R.id.btn_save);
        userModels = new UserModels();
        UID = "";
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            UID = GoogleSignIn.getLastSignedInAccount(this).getId();
        }
        else if (auth.getCurrentUser() != null){
            UID = auth.getCurrentUser().getUid();
        }



        ActivityResultLauncher<String> imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        img_chosen.setImageURI(result);

                        uriAnh = result;
                    }
                });

        btn_choose.setOnClickListener(view -> imageResultLauncher.launch("image/*"));
        //Chọn nhạc
        ActivityResultLauncher<String> audioResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        //link_song = result.toString();
                        if (new File(result.toString()).exists()){
                            uriNhac = result;
                        }
                        Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        btnChonBH.setOnClickListener(view -> audioResultLauncher.launch("audio/*"));
        //Exit
        btn_exit.setOnClickListener(view -> {
//            Intent intent = new Intent(AddSongActivity.this,MainActivity.class);
//            startActivity(intent);
            finish();

        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            if (UID.equals(snapshot1.child("uid").getValue().toString())){
                                userModels = snapshot1.getValue(UserModels.class);
                                key = snapshot1.getKey();
                            }
                        }

                        songObject.setNameSong(edtTenBH.getText().toString());
                        songObject.setArtist(edtArtist.getText().toString());

                        ProgressDialog dialog = new ProgressDialog(AddSongActivity.this);
                        dialog.show();

                        reference = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                                .getReference("Nhac").child(uriNhac.getLastPathSegment());

                        reference.putFile(uriNhac).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                while (!task.isComplete());
                                uriNhac = task.getResult();
                                songObject.setLinkSong(uriNhac.toString());
                                dialog.dismiss();

                                UpAnhLenStorage();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddSongActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                int CurProgress = (int) progress;
                                dialog.setMessage("Upload: " + CurProgress + "%");
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void UpAnhLenStorage(){

        ProgressDialog dialog = new ProgressDialog(AddSongActivity.this);
        dialog.show();

        reference = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                .getReference("AnhNhac").child(uriNhac.getLastPathSegment());

        reference.putFile(uriAnh).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                uriAnh = task.getResult();
                songObject.setImgSong(uriAnh.toString());
                userModels.getListSong().add(songObject);
                dialog.dismiss();

                database.child(key).setValue(userModels).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddSongActivity.this, "Thêm nhạc thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSongActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                int CurProgress = (int) progress;
                dialog.setMessage("Upload: " + CurProgress + "%");
            }
        });


    }

}