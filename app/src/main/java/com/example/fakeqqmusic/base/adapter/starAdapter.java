package com.example.fakeqqmusic.base.adapter;

import static com.example.fakeqqmusic.ui.musicplay.MusicActivity.PARAM_MUSIC_LIST;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.StarItemBinding;
import com.example.fakeqqmusic.service.MusicService;
import com.example.fakeqqmusic.ui.MainActivity;
import com.example.fakeqqmusic.ui.WelcomeActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicModel;

import java.io.Serializable;
import java.util.List;

public class starAdapter extends RecyclerView.Adapter<starAdapter.ViewHolder>{
    private List<musicData> starItemList;

    public starAdapter(List<musicData> starItemList) {
        this.starItemList = starItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StarItemBinding binding = StarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        musicData currentItem1 = starItemList.get(position);
        musicData.DataDTO currentItem = currentItem1.getData();
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
        Glide.with(holder.itemView)
                .load(currentItem.getPicurl())
                .into(holder.binding.imageView);
        holder.binding.musicName.setText(currentItem.getName());
        holder.binding.musicSinger.setText(currentItem.getArtistsname());
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MusicActivity.class);
            intent.putExtra("MUSIC_INDEX", position);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return starItemList == null ? 0 : starItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public boolean isNo = false;
        StarItemBinding binding;
        public ViewHolder(StarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
