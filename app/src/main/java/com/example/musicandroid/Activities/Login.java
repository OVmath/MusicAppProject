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

import com.example.musicandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView txtChuaCoTK;
    EditText TK, MK;
    Button btnDN;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TK = findViewById(R.id.edtEmailLogin);
        MK = findViewById(R.id.edtLogninPass);
        btnDN = findViewById(R.id.btnLogin);
        txtChuaCoTK = findViewById(R.id.txtChuaCoTK);
        auth = FirebaseAuth.getInstance();

        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });

        txtChuaCoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void DangNhap(){
        String mail = TK.getText().toString(), pass = MK.getText().toString();
        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                    finish();
                }else Toast.makeText(Login.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

