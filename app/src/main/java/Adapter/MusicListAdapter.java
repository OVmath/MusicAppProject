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

import com.example.musicandroid.Activities.MainActivity;
import com.example.musicandroid.Activities.MusicPlayerActivity;
import com.example.musicandroid.Models.MyMediaPlayer;
import com.example.musicandroid.R;
import com.example.musicandroid.Models.SongObject;
import com.squareup.picasso.Picasso;

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
        Picasso.with(context.getApplicationContext()).load(songData.getImgSong()).into(holder.iconImageView);
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
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
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
        holder.imgMenu.setOnClickListener(view -> ((MainActivity) context).showDialogMenu(songData, position));
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
    public void clear(){
        int size = songsList.size();
        notifyItemRangeRemoved(0, size);
    }
}
