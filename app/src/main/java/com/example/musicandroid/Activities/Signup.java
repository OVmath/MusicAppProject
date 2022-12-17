package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.Models.UserModel;
import com.example.musicandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextView txtCoTK;
    EditText TK, MK, XacNhanMK;
    Button DK;
    FirebaseAuth auth;
    DatabaseReference database;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TK = findViewById(R.id.edtChangeCurPass);
        MK = findViewById(R.id.edtChangeNewPass);
        XacNhanMK = findViewById(R.id.edtChangeConfirmNewPass);
        txtCoTK = findViewById(R.id.txtCoTK);
        DK = findViewById(R.id.btnSignUp);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("user");

        DK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangKy();
            }
        });

        txtCoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaCoTKEvent();
            }
        });

    }

    public void DangKy(){

        String Mail = TK.getText().toString();
        String Pass = MK.getText().toString();
        String Pass2 = XacNhanMK.getText().toString();
        if (Pass.equals(Pass2)){
            auth.createUserWithEmailAndPassword(Mail, Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        user = new UserModel(auth.getCurrentUser().getUid());
                        database.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signup.this, Login.class));
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(Signup.this, "Tên đăng nhập không phù hợp", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu chưa trùng nhau.", Toast.LENGTH_SHORT).show();

    }

    public void DaCoTKEvent(){

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();//kết thúc tất cả các activity trong cùng một ngăn xếp nhiệm vụ
        System.exit(0);

    }
}