package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    TextView txtChuaCoTK;
    EditText TK, MK;
    Button btnDN;
    ImageView signInWithGG, signInWithFace;
    CheckBox rememberAcc;
    FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TK = findViewById(R.id.edtEmailLogin);
        MK = findViewById(R.id.edtLogninPass);
        btnDN = findViewById(R.id.btnLogin);
        txtChuaCoTK = findViewById(R.id.txtChuaCoTK);
        signInWithGG = findViewById(R.id.SignInGGAcc);
        signInWithFace = findViewById(R.id.SignInFaceAcc);
        rememberAcc = findViewById(R.id.checkBoxRemember);
        auth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        callbackManager = CallbackManager.Factory.create();

        SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
        if (!(preferences.getString("Mail", "").equals("") && preferences.getString("Pass", "").equals(""))){

            String mail = preferences.getString("Mail", "");
            String pass = preferences.getString("Pass", "");

            auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(Login.this, "Đăng nhập thành công..", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        signInWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhapVoiTKGG();
            }
        });

        signInWithFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_ONLY);
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile"));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(Login.this, "Đăng nhập bằng tài khoản facebook thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "Đã tắt...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login.this, exception.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                });

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

        if (rememberAcc.isChecked()){

            SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Mail", mail);
            editor.putString("Pass", pass);
            editor.apply();

        }
        else {
            SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Mail", "");
            editor.putString("Pass", "");
            editor.apply();
        }

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

    public void DangNhapVoiTKGG(){

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 01);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 01){
            GoogleSignInAccount Acc = GoogleSignIn.getLastSignedInAccount(this);
            try {

                Toast.makeText(this, "Đăng nhập vào " + Acc.getEmail() +  " thành công..", Toast.LENGTH_SHORT).show();
                finish();
            }catch (Exception ex){
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
