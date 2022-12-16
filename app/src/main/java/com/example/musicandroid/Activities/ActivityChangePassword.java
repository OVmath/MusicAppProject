package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityChangePassword extends AppCompatActivity {

    //Liem code start
    EditText edtCurPass, edtNewPass, edtConfirmNewPass;
    Button btnChangePass;
    FirebaseUser auth;
    //liem end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //Liem code start
        edtCurPass = findViewById(R.id.edtChangeCurPass);
        edtNewPass = findViewById(R.id.edtChangeNewPass);
        btnChangePass = findViewById(R.id.btnChangePassSetting);
        edtConfirmNewPass = findViewById(R.id.edtChangeConfirmNewPass);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNewPass.getText().toString().equals(edtConfirmNewPass.getText().toString())){
                    AuthCredential credential = EmailAuthProvider.getCredential(auth.getEmail(), edtCurPass.getText().toString());
                    auth.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                auth.updatePassword(edtNewPass.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(ActivityChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        //liem end
    }
}