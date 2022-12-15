package com.example.musicandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicandroid.MainActivity;
import com.example.musicandroid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {

    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    BottomNavigationView bottomNavigationViewSetting;
    FloatingActionButton btnInfoUpdate;
    TextView tvHelloAcc;
    //end
    Button logout;
    LinearLayout aboutUs;
    LinearLayout helpSupport;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //liem code
        bottomNavigationViewSetting = findViewById(R.id.bottom_navi_menu_setting_activity);
        btnInfoUpdate = findViewById(R.id.btnInfoUpdate);
        tvHelloAcc = findViewById(R.id.tvHelloAccMusicScreen);
        aboutUs = findViewById(R.id.aboutUs);
        helpSupport = findViewById(R.id.helpSupport);
        logout = findViewById(R.id.logout);

        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            Toast.makeText(this, GoogleSignIn.getLastSignedInAccount(this).getId(), Toast.LENGTH_SHORT).show();
            tvHelloAcc.setText("Hello " + GoogleSignIn.getLastSignedInAccount(this).getDisplayName());
        }
        else if (auth.getCurrentUser() != null){
            tvHelloAcc.setText("Hello " + auth.getCurrentUser().getEmail());
        }
        else {
            startActivity(new Intent(getApplicationContext(), OnboardingScreen1.class));
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

        btnInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, SuaInfoActivity.class));
            }
        });
        //end
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, Login.class));
            }
        });
        helpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, about.class));
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this, helpAndSupport.class));
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