package com.example.fakeqqmusic.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.StarItemBinding;

import java.util.List;

public class MusicDataAdapter extends RecyclerView.Adapter<MusicDataAdapter.MusicDataHolder> {
    public List<musicData> musicDataList;
    public void setMusicDataList(List<musicData> musicDataList) {
        this.musicDataList = musicDataList;
    }

    @NonNull
    @Override
    public MusicDataAdapter.MusicDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StarItemBinding binding = StarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MusicDataHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicDataAdapter.MusicDataHolder holder, int position) {
        musicData musicData = musicDataList.get(position);

    }

    @Override
    public int getItemCount() {
        if (musicDataList != null)
            return musicDataList.size();
        return 0;
    }

    public class MusicDataHolder extends RecyclerView.ViewHolder {
        public StarItemBinding binding;
        public MusicDataHolder(StarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
