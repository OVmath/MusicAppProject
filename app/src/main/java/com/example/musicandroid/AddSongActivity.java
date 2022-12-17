package com.example.musicandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.musicandroid.Models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.Calendar;


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
    UserModel userModel;
    String UID;
    Uri uriAnh, uriNhac;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    StorageReference referenceNhac, referenceAnh;
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
                        uriNhac = result;
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
                                userModel = snapshot1.getValue(UserModel.class);
                                key = snapshot1.getKey();
                            }
                        }

                        songObject.setNameSong(edtTenBH.getText().toString());
                        songObject.setArtist(edtArtist.getText().toString());

                        referenceNhac = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                                .getReference("Nhac").child(uriNhac.getLastPathSegment());

                        referenceNhac.putFile(uriNhac).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                while (!task.isComplete());
                                uriNhac = task.getResult();
                                songObject.setLinkSong(uriNhac.toString());

                                UpAnhLenStorage();

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

        ProgressDialog dialog2 = new ProgressDialog(AddSongActivity.this);
        dialog2.show();

        referenceAnh = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                .getReference("AnhNhac").child(uriNhac.getLastPathSegment());

        referenceAnh.putFile(uriAnh).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                uriAnh = task.getResult();
                songObject.setImgSong(uriAnh.toString());
                songObject.setKeySong(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                userModel.getListSong().add(songObject);

                dialog2.dismiss();

                database.child(key).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddSongActivity.this, "Thêm nhạc thành công", Toast.LENGTH_SHORT).show();
                            finish();
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

}