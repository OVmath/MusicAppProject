package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.MusicPlayerActivity;
import com.example.musicandroid.MyMediaPlayer;
import com.example.musicandroid.R;
import com.example.musicandroid.SongObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TrendingRvAdapter extends RecyclerView.Adapter<TrendingRvAdapter.TrendingRvHolder> {

    private ArrayList<SongObject> listTrending;
    Activity context;

    public TrendingRvAdapter(ArrayList<SongObject> songsList, Activity context) {
        this.listTrending = songsList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrendingRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent,false);
        return new TrendingRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingRvHolder holder, int position) {

        SongObject songData = listTrending.get(position);
        Picasso.with(context.getApplicationContext()).load(songData.getImgSong()).into(holder.imgTrending);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",listTrending);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrending.size();
    }

    class TrendingRvHolder extends RecyclerView.ViewHolder {

        ImageView imgTrending;

        public TrendingRvHolder(@NonNull View itemView) {
            super(itemView);
            imgTrending = itemView.findViewById(R.id.imgTrending);

        }
    }

}
