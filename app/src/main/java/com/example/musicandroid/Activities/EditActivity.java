package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicandroid.Models.UserModel;
import com.example.musicandroid.R;
import com.example.musicandroid.SongObject;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                songObject.setNameSong(edtName.getText().toString());
                songObject.setArtist(edtArtist.getText().toString());

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
        });

    }
}