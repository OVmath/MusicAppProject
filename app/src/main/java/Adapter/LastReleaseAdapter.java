package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.Activities.MusicPlayerActivity;
import com.example.musicandroid.Models.MyMediaPlayer;
import com.example.musicandroid.R;
import com.example.musicandroid.Models.SongObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LastReleaseAdapter extends RecyclerView.Adapter<LastReleaseAdapter.ArtistHolder> {

    ArrayList<SongObject> artistModelsArrayList;
    Activity context;

    public LastReleaseAdapter(ArrayList<SongObject> artistModelsArrayList, Activity context) {
        this.artistModelsArrayList = artistModelsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_release_item, parent, false);

        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        SongObject songObject = artistModelsArrayList.get(position);
        holder.tvArtistName.setText(songObject.getNameSong());
        Picasso.with(context.getApplicationContext()).load(songObject.getImgSong()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",artistModelsArrayList);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return artistModelsArrayList.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{

        TextView tvArtistName;
        ImageView img;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            img = itemView.findViewById(R.id.imgArtist);
        }
    }

}
