package Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.Models.TrendingModels;
import com.example.musicandroid.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TrendingRvAdapter extends RecyclerView.Adapter<TrendingRvAdapter.TrendingRvHolder> {

    private ArrayList<TrendingModels> listTrending;

    public TrendingRvAdapter(ArrayList<TrendingModels> listTrending) {
        this.listTrending = listTrending;
    }

    @NonNull
    @Override
    public TrendingRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent,false);
        return new TrendingRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingRvHolder holder, int position) {

        int src = Integer.parseInt(listTrending.get(position).getImgSrc());
        holder.imgTrending.setImageResource(src);

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
