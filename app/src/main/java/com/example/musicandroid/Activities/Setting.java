package com.example.musicandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicandroid.Models.UserModel;
import com.example.musicandroid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Setting extends AppCompatActivity {

    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    BottomNavigationView bottomNavigationViewSetting;
    LinearLayout btnInfoUpdate, btnChangePass, btnAboutUs, connect, tvDataSaver, dataDown,downloadQuality, helpSupport, aboutUs;
    TextView tvHelloAcc, tvHelloAcc2;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    String UID;
    ImageView AnhDaiDienSetting;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    //end
    Button logout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //liem code
        connect = findViewById(R.id.connect);
        tvDataSaver = findViewById(R.id.tvDataSaver);
        dataDown = findViewById(R.id.dataDown);
        downloadQuality = findViewById(R.id.downloadQuality);
        bottomNavigationViewSetting = findViewById(R.id.bottom_navi_menu_setting_activity);
        btnInfoUpdate = findViewById(R.id.LayoutInfoUpdate);
        btnChangePass = findViewById(R.id.LayoutChangePass);
        tvHelloAcc = findViewById(R.id.tvHelloAccMusicScreen);
        tvHelloAcc2 = findViewById(R.id.tvHelloAccMusicScreen2);
        aboutUs = findViewById(R.id.aboutUs);
        helpSupport = findViewById(R.id.helpSupport);
        logout = findViewById(R.id.logout);
        AnhDaiDienSetting = findViewById(R.id.imgAnhSetting);
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
                        userModel = snapshot1.getValue(UserModel.class);
                    }
                }
                if (userModel.getTenHT().equals("")){
                    tvHelloAcc.setText("Vào setting để chỉnh tên hiển thị");
                    tvHelloAcc.setTextSize(20);
                    tvHelloAcc2.setText("");
                }
                else{
                    tvHelloAcc.setText(userModel.getTenHT());
                    tvHelloAcc2.setText(userModel.getTenHT());
                }

                if (!userModel.getLinkAnh().equals("")){
                    Picasso.with(Setting.this).load(userModel.getLinkAnh()).into(AnhDaiDienSetting);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, ActivityChangePassword.class));
            }
        });
        //liem end

        btnInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, SuaInfoActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                SharedPreferences preferences = getSharedPreferences("remember", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Mail", "");
                editor.putString("Pass", "");
                editor.apply();
                startActivity(new Intent(Setting.this, Login.class));
            }
        });
        helpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, helpAndSupport.class));
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, about.class));
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, thongBao.class));
            }
        });
        tvDataSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, thongBao.class));
            }
        });
        dataDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, thongBao.class));
            }
        });
        downloadQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, thongBao.class));
            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navi_menu_setting_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.btn_music_page){
                startActivity(new Intent(Setting.this,MusicScreen.class));
                return true;
            }
            else if (item.getItemId()==R.id.btn_home){
                startActivity(new Intent(Setting.this,MainActivity.class));
                return true;
            }
            else {
                return true;
            }
        });

    }



}