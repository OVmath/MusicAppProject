package com.example.musicandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicandroid.Activities.LyricsFragment;
import com.example.musicandroid.Models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView titleTv,currentTimeTv,totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay,nextBtn,previousBtn,musicIcon;
    ImageView img_back;
    ImageView replay;
    ImageView img_lyric;
    CircleImageView img_like;
    ArrayList<SongObject> songsList;
    SongObject currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    LyricsFragment fragment;
    Random random = new Random();
    int x=0;
    //liem code
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView tvHelloAcc;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient gsc;
    UserModel userModel;
    String UID, keyUser;
    ImageView AnhDaiDienMain;
    DatabaseReference database = FirebaseDatabase.getInstance("https://musicandroidjava-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user");
    //end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.timeCurrent);
        totalTimeTv = findViewById(R.id.timeEnd);
        seekBar = findViewById(R.id.seekBar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.imgSong);
        img_back = findViewById(R.id.img_back);
        img_like = findViewById(R.id.img_like);
        replay = findViewById(R.id.img_replay);
        img_lyric = findViewById(R.id.ic_lyrics);
        fragment = new LyricsFragment();
        img_lyric.setOnClickListener(view -> {

            //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
//            fragment.getView().setVisibility(View.VISIBLE);
        });
        img_like.setOnClickListener(view -> {

        });
        replay.setOnClickListener(view -> {
            setResourcesWithMusic();
        });
        img_back.setOnClickListener(view -> {
            finish();
            mediaPlayer.pause();
        });
        titleTv.setSelected(true);
        songsList = (ArrayList<SongObject>) getIntent().getSerializableExtra("LIST");

        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            UID = GoogleSignIn.getLastSignedInAccount(this).getId();
        }
        else if (auth.getCurrentUser() != null){
            UID = auth.getCurrentUser().getUid();
        }

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if (UID.equals(snapshot1.child("uid").getValue().toString())){
                        userModel = snapshot1.getValue(UserModel.class);
                        keyUser = snapshot1.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run  () {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        musicIcon.setRotation(x++);
                    }else{
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                        musicIcon.setRotation(0);
                    }

                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
                if (progress == mediaPlayer.getDuration()){
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playRandomSong();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        Picasso.with(MusicPlayerActivity.this).load(currentSong.getImgSong()).into(musicIcon);

        titleTv.setText(currentSong.getNameSong());
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(currentSong.getLinkSong());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalTimeTv.setText(convertToMMSS(mediaPlayer.getDuration() + ""));
        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentSong.getLiked()){
                    currentSong.setLiked(false);
                    for (int i = 0; i < userModel.getListSong().size(); i++){
                        if (userModel.getListSong().get(i).getKeySong().equals(currentSong.getKeySong())){
                            database.child(keyUser).child("listSong").child(i + "").setValue(currentSong);
                        }
                    }
                    img_like.setCircleBackgroundColor(Color.parseColor("#3C0051"));
                }
                else {
                    currentSong.setLiked(true);
                    for (int i = 0; i < userModel.getListSong().size(); i++){
                        if (userModel.getListSong().get(i).getKeySong().equals(currentSong.getKeySong())){
                            database.child(keyUser).child("listSong").child(i + "").setValue(currentSong);
                        }
                    }
                    img_like.setCircleBackgroundColor(Color.RED);
                }

            }
        });
        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        previousBtn.setOnClickListener(v-> playPreviousSong());
        nextBtn.setOnLongClickListener(v -> playRandomSong());
        previousBtn.setOnLongClickListener(v -> playRandomSong());
        playMusic();

    }

    private boolean playRandomSong() {
        int i = MyMediaPlayer.currentIndex;
        while (MyMediaPlayer.currentIndex == i){
            if (MyMediaPlayer.currentIndex != i){
                break;
            }
            MyMediaPlayer.currentIndex = random.nextInt(songsList.size()-1);
        }
        mediaPlayer.reset();
        setResourcesWithMusic();
        return true;
    }


    private void playMusic(){

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getLinkSong());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.start();

                }
            });
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong(){

        if(MyMediaPlayer.currentIndex== songsList.size()-1)
            MyMediaPlayer.currentIndex = 0;
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        if(MyMediaPlayer.currentIndex== 0)
            MyMediaPlayer.currentIndex = songsList.size()-1;
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mediaPlayer.pause();
    }
}