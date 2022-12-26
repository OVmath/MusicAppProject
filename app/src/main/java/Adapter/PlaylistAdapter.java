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

import com.example.musicandroid.Activities.PlaylistActivity;
import com.example.musicandroid.Activities.PlaylistItemActivity;
import com.example.musicandroid.Models.PlaylistObject;
import com.example.musicandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    ArrayList<PlaylistObject> playlistsList;
    Activity context;

    public PlaylistAdapter(ArrayList<PlaylistObject> playlistsList, Activity context) {
        this.playlistsList = playlistsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.playlist_item,parent,false);
        return new PlaylistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        PlaylistObject playlistData = playlistsList.get(position);
        holder.tvName.setText(playlistData.getNamePlaylist());
        Picasso.with(context.getApplicationContext()).load(playlistData.getImgPath()).into(holder.imgPlaylist);
//        holder.imgMenu.setOnClickListener(view -> {
//            Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
//            dialog.setContentView(R.layout.dialog_menu);
////            Button add = dialog.findViewById(R.id.btn_add);
//            Button edit= dialog.findViewById(R.id.btn_edit);
//            Button delete = dialog.findViewById(R.id.btn_delete);
//            Button exit = dialog.findViewById(R.id.btn_exit);
//            exit.setOnClickListener(view1 -> dialog.dismiss());
//            dialog.show();
//        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlaylistItemActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        holder.imgMenu.setOnClickListener(view -> {
            ((PlaylistActivity) context).showDialogMenu(playlistData);
        });
    }

    @Override
    public int getItemCount() {
        return playlistsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlaylist;
        ImageView imgMenu;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlaylist = itemView.findViewById(R.id.imgPlaylist);
            imgMenu = itemView.findViewById(R.id.imgMenu);
            tvName  = itemView.findViewById(R.id.tvPlaylist);
        }
    }
    public void clear(){
        int size = playlistsList.size();
        notifyItemRangeRemoved(0, size);
    }
}
