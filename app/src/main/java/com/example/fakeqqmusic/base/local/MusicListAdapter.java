package com.example.fakeqqmusic.base.local;

import androidx.annotation.Nullable;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.MusicInfoContract;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {
    public static class DateTimeUtils {
        public static String formatTime(int duration) {
            int minutes = duration / 60;
            int seconds = duration % 60;
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


    private OnItemClickListener listener;
    private List<Song> songList;
    private boolean isPlay;

    // 构造函数
    public MusicListAdapter(List<Song> songList,OnItemClickListener listener) {
        this.songList = songList;
        this.listener = listener;

    }

    // 创建 ViewHolder
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_item, parent, false);
        return new MusicViewHolder(view);
    }

        public void onBindViewHolder(@NonNull MusicViewHolder holder, int position){
            Song song = songList.get(position);
            String time = DateTimeUtils.formatTime(song.getDuration());
            // 设置控件内容
            holder.songName.setText(song.getSong());
            holder.singer.setText(song.getSinger() + " - " + song.getAlbum());
            holder.duration.setText(time);
            holder.position.setText(String.valueOf(position + 1));

            // 根据选中状态设置文本颜色
            int color = song.isCheck() ? R.color.qq_base : R.color.white;
            holder.songName.setTextColor(holder.itemView.getContext().getColor(color));
            holder.singer.setTextColor(holder.itemView.getContext().getColor(color));
            holder.duration.setTextColor(holder.itemView.getContext().getColor(color));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取当前 ViewHolder 所代表的项的位置
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(currentPosition,v);
                    }
                }
            });


        }

    public void changeState() {
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return songList.size();
    }

    // ViewHolder 类
    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView songName, singer, duration, position;

        public MusicViewHolder(View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.tv_song_name);
            singer = itemView.findViewById(R.id.tv_singer);
            duration = itemView.findViewById(R.id.tv_duration_time);
            position = itemView.findViewById(R.id.tv_position);
        }
    }


}
