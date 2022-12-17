package com.example.musicandroid.Activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicandroid.Models.UserModels;
import com.example.musicandroid.R;
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

import java.util.ArrayList;

public class SuaInfoActivity extends AppCompatActivity {

    //liem code start
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModels userModels;
    String UID;
    Uri uri;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    StorageReference reference;
    ArrayList<UserModels> listUser;
    EditText edtSuaTen, edtSuaGioi;
    Button btnLuuSua;
    String key;
    Button btnSuaAnh;
    ImageView imgSuaAnh;
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_info);
        //Liem code start
        btnLuuSua = findViewById(R.id.btnLuuSua);
        edtSuaTen = findViewById(R.id.edtSuaTenHT);
        edtSuaGioi = findViewById(R.id.edtSuaGioi);
        btnSuaAnh = findViewById(R.id.btnSuaAnhDaiDien);
        imgSuaAnh = findViewById(R.id.imgSuaAnhDaiDien);
        listUser = new ArrayList<>();
        userModels = new UserModels();
        UID = "";
        userModels = new UserModels();
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
                        key = snapshot1.getKey();
                    }
                }
                String ten = userModels.getTenHT();
                String gioi = userModels.getGioiTinh();

                if (ten.equals("")){
                    edtSuaTen.setHint("chưa chỉnh tên hiển thị");
                }
                else edtSuaTen.setText(ten);

                if (gioi.equals("")){
                    edtSuaGioi.setHint("chưa chỉnh giới tính");
                }
                else edtSuaGioi.setText(gioi);
                if (!userModels.getLinkAnh().equals("")) Picasso.with(SuaInfoActivity.this).load(userModels.getLinkAnh()).into(imgSuaAnh);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSuaAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        btnLuuSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SuaInfoActivity.this);
                builder.setTitle("Lưu dữ liệu thông tin cá nhân");
                builder.setMessage("Bạn có chắc muốn lưu thông tin cá nhân của mình không??");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userModels.setTenHT(edtSuaTen.getText().toString());
                        userModels.setGioiTinh(edtSuaGioi.getText().toString());
                        ProgressDialog dialog = new ProgressDialog(SuaInfoActivity.this);
                        dialog.show();

                        reference = FirebaseStorage.getInstance("gs://musicandroidjava.appspot.com/")
                                .getReference("AnhDaiDien").child(uri.getLastPathSegment());

                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                while (!task.isComplete());
                                uri = task.getResult();
                                userModels.setLinkAnh(uri.toString());
                                dialog.dismiss();

                                database.child(key).setValue(userModels).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(SuaInfoActivity.this, "Sửa thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SuaInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            try {
                uri = data.getData();
                Bitmap bitmap =MediaStore.Images.Media.getBitmap(
                        getContentResolver(),uri
                );
                imgSuaAnh.setImageBitmap(bitmap);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }
    //end
}