package com.example.fakeqqmusic.base.adapter;

import static com.example.fakeqqmusic.ui.musicplay.MusicActivity.PARAM_MUSIC_LIST;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.StarItemBinding;
import com.example.fakeqqmusic.service.MusicService;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicModel;

import java.io.Serializable;
import java.util.List;

public class MyMusicListAdapter extends RecyclerView.Adapter<MyMusicListAdapter.ViewHolder>{
    private List<musicData> ItemList;
    public MyMusicListAdapter(List<musicData> ItemList)
    {
        this.ItemList = ItemList;
    }
    @NonNull
    @Override
    public MyMusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StarItemBinding binding = StarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMusicListAdapter.ViewHolder holder, int position) {
        musicData currentItem = ItemList.get(position);
        musicData.DataDTO currentItem1 = currentItem.getData();
        Glide.with(holder.itemView)
                .load(currentItem1.getPicurl())
                .into(holder.binding.imageView);
        holder.binding.musicName.setText(currentItem1.getName());
        holder.binding.musicSinger.setText(currentItem1.getArtistsname());
        holder.binding.musicNice.setOnClickListener(v -> {
            if(holder.isNo){
                holder.binding.musicNice.setImageResource(R.drawable.icon_nice_no);
                holder.isNo = !holder.isNo;
            }
            else{
                holder.binding.musicNice.setImageResource(R.drawable.icon_nice_red);
                holder.isNo = !holder.isNo;
            }
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            v.getContext().startActivity(new Intent( v.getContext(), MusicActivity.class));
        });
    }


    @Override
    public int getItemCount() {
        return ItemList == null ? 0 : ItemList.size();
    }
    //隐藏
    public void setHideList(List<musicData> newList) {
        this.ItemList = newList;
        notifyDataSetChanged();
    }

    //展开
    public void setOpenList(List<musicData> openList) {
        this.ItemList = openList;
        notifyDataSetChanged();
    }

    //不需要隐藏/展开
    public void setRealList(List<musicData> realList) {
        this.ItemList = realList;
        notifyDataSetChanged();
    }

    //清除数据
    public void clearData() {
        if (ItemList != null) {
            this.ItemList.clear();
            notifyDataSetChanged();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public boolean isNo = false;
        StarItemBinding binding;
        TextView title;
        public ViewHolder(@NonNull StarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
