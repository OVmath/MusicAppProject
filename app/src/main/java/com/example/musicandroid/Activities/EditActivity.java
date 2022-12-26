package com.example.musicandroid.Activities;

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
import com.example.musicandroid.R;
import com.example.musicandroid.Models.SongObject;
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

public class EditActivity extends AppCompatActivity {

    EditText edtName, edtArtist;
    Button btn_image, btn_exit, btn_save;
    ImageView image;

    //liem code start
    FirebaseAuth auth = FirebaseAuth.getInstance();
    UserModel userModel;
    String UID;
    Uri uriAnh, uriNhac;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    StorageReference referenceNhac, referenceAnh;
    String keyUser, keySong;
    SongObject songObject;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtName = findViewById(R.id.edtName);
        edtArtist = findViewById(R.id.edtArtist);
        btn_image = findViewById(R.id.btn_image);
        btn_exit = findViewById(R.id.btn_exit);
        btn_save = findViewById(R.id.btn_save);
        image = findViewById(R.id.image);

        songObject = (SongObject) getIntent().getSerializableExtra("Object");

        userModel = new UserModel();
        UID = "";
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

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (UID.equals(snapshot1.child("uid").getValue().toString())) {
                        userModel = snapshot1.getValue(UserModel.class);
                        keyUser = snapshot1.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActivityResultLauncher<String> imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        image.setImageURI(result);

                        uriAnh = result;
                    }
                });

        btn_image.setOnClickListener(view -> imageResultLauncher.launch("image/*"));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                songObject.setNameSong(edtName.getText().toString());
                songObject.setArtist(edtArtist.getText().toString());

                ProgressDialog dialog2 = new ProgressDialog(EditActivity.this);
                dialog2.show();

                referenceAnh = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                        .getReference("AnhNhac").child(uriAnh.getLastPathSegment());

                referenceAnh.putFile(uriAnh).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                        while (!task.isComplete());
                        uriAnh = task.getResult();
                        songObject.setImgSong(uriAnh.toString());

                        dialog2.dismiss();

                        for (int i = 0; i < userModel.getListSong().size(); i++){
                            if (userModel.getListSong().get(i).getKeySong().equals(songObject.getKeySong())){
                                database.child(keyUser).child("listSong").child(i + "").setValue(songObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditActivity.this, "Sửa nhạc thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        }

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
        });

    }
}