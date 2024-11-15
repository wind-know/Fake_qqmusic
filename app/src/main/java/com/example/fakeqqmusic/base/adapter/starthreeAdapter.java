package com.example.fakeqqmusic.base.adapter;


import static com.example.fakeqqmusic.ui.musicplay.MusicActivity.PARAM_MUSIC_LIST;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.StarItemThreeBinding;
import com.example.fakeqqmusic.service.MusicService;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicModel;

import java.io.Serializable;
import java.util.List;

public class starthreeAdapter extends RecyclerView.Adapter<starthreeAdapter.ViewHolder>{
    StarItemThreeBinding binding;
    List<List<musicData>> mlist;
    public void starthreeAdapter(List<List<musicData>> mlist)
    {
        this.mlist = mlist;
    }
    @NonNull
    @Override
    public starthreeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = StarItemThreeBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull starthreeAdapter.ViewHolder holder, int position) {
        List<musicData> currentItem1 = mlist.get(position);
        musicData.DataDTO current1 = currentItem1.get(0).getData();
        Glide.with(holder.itemView)
                .asBitmap()
                .load(current1.getPicurl())
                .into(holder.binding.imageView);
        holder.binding.musicName.setText(current1.getName());
        holder.binding.musicSinger.setText(current1.getArtistsname());
        musicData.DataDTO current2 = currentItem1.get(1).getData();
        Glide.with(holder.itemView)
                .asBitmap()
                .load(current2.getPicurl())
                .into(holder.binding.imageView2);
        holder.binding.musicName2.setText(current2.getName());
        holder.binding.musicSinger2.setText(current2.getArtistsname());
        musicData.DataDTO current3 = currentItem1.get(2).getData();
        Glide.with(holder.itemView)
                .asBitmap()
                .load(current3.getPicurl())
                .into(holder.binding.imageView3);
        holder.binding.musicName3.setText(current3.getName());
        holder.binding.musicSinger3.setText(current3.getArtistsname());
        setListener(holder);
        holder.binding.getRoot().setOnClickListener(v -> {
            v.getContext().startActivity(new Intent( v.getContext(), MusicActivity.class));
        });
    }
    public void setListener(starthreeAdapter.ViewHolder holder){
        holder.binding.musicNice.setOnClickListener(v -> {
            Log.d("TAG", "onClick: ");
            if(holder.isNo[0]){
                holder.binding.musicNice.setImageResource(R.drawable.icon_nice_no);
                holder.isNo[0] = !holder.isNo[0];
                Log.d("TAG", "NO");
            }
            else{
                holder.binding.musicNice.setImageResource(R.drawable.icon_nice_red);
                holder.isNo[0] = !holder.isNo[0];
            }
        });
        holder.binding.musicNice2.setOnClickListener(v -> {
            if(holder.isNo[1]){
                holder.binding.musicNice2.setImageResource(R.drawable.icon_nice_no);
                holder.isNo[1] = !holder.isNo[1];
                Log.d("TAG", "NO");
            }
            else{
                holder.binding.musicNice2.setImageResource(R.drawable.icon_nice_red);
                holder.isNo[1] = !holder.isNo[1];
            }

        });
        holder.binding.musicNice3.setOnClickListener(v -> {
            if(holder.isNo[2]){
                holder.binding.musicNice3.setImageResource(R.drawable.icon_nice_no);
                holder.isNo[2] = !holder.isNo[2];
            }
            else{
                holder.binding.musicNice3.setImageResource(R.drawable.icon_nice_red);
                holder.isNo[2] = !holder.isNo[2];
           }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StarItemThreeBinding binding;
        boolean[] isNo = new boolean[]{false,false,false};
        public ViewHolder(StarItemThreeBinding binding) {
            super(binding.getRoot());
            this.binding =  binding;
        }
    }
}
