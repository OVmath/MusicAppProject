package com.example.musicandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    ArrayList<SongObject> songsList;
    Activity context;

    public MusicListAdapter(ArrayList<SongObject> songsList, Activity context) {
        this.songsList = songsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.ViewHolder holder, int position) {
        SongObject songData = songsList.get(position);
        holder.titleTextView.setText(songData.getNameSong());
//        if(MyMediaPlayer.currentIndex==position){
//            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
//        }else{
//            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context,MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
//        holder.imgMenu.setOnClickListener(view -> {
//            Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
//            dialog.setContentView(R.layout.dialog_menu);
//            Button add = dialog.findViewById(R.id.btn_add);
//            Button edit= dialog.findViewById(R.id.btn_edit);
//            Button delete = dialog.findViewById(R.id.btn_delete);
//            Button exit = dialog.findViewById(R.id.btn_exit);
//            exit.setOnClickListener(view1 -> {
//                dialog.dismiss();
//            });
//            dialog.show();
//        });
        holder.imgMenu.setOnClickListener(view -> ((MainActivity) context).showDialogMenu(songData));
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;
        ImageView imgMenu;
        public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.tvSongName);
                iconImageView = itemView.findViewById(R.id.iconSong);
                imgMenu = itemView.findViewById(R.id.imgMenu);
            }
        }
}

