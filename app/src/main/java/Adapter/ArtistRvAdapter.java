package Adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.Models.ArtistModels;
import com.example.musicandroid.R;

import java.util.ArrayList;

public class ArtistRvAdapter extends RecyclerView.Adapter<ArtistRvAdapter.ArtistHolder> {

    ArrayList<ArtistModels> artistModelsArrayList;

    public ArtistRvAdapter(ArrayList<ArtistModels> artistModelsArrayList) {
        this.artistModelsArrayList = artistModelsArrayList;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        holder.tvArtistName.setText(artistModelsArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return artistModelsArrayList.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{

        TextView tvArtistName;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
        }
    }

}
