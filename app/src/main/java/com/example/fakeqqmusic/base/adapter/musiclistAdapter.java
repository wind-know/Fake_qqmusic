package com.example.fakeqqmusic.base.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.ItemMusicListBigBinding;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.google.android.material.card.MaterialCardView;

import androidx.palette.graphics.Palette;


import java.util.List;

public class musiclistAdapter extends RecyclerView.Adapter<musiclistAdapter.ViewHolder> {
    private List<musicData> ItemList;

    public musiclistAdapter(List<musicData> ItemList) {
        this.ItemList = ItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMusicListBigBinding binding = ItemMusicListBigBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        musicData currentItem1 = ItemList.get(position);
        musicData.DataDTO currentItem = currentItem1.getData();
        // 使用 Glide 加载图片
        Glide.with(holder.itemView)
                .asBitmap()  // 使用 asBitmap() 来获得 Bitmap
                .load(currentItem.getPicurl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.binding.imageView.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette != null) {
                                    // 提取主色、明亮的颜色等
                                    int vibrantColor = palette.getVibrantColor(Color.BLACK); // 主色
                                    int darkMutedColor = palette.getDarkMutedColor(Color.BLACK); // 暗色
                                    // 设置按钮的图标颜色
                                    Drawable iconDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.icon_play_1);
                                    Drawable drawable = holder.binding.buttonPlay.getBackground();
                                    if (drawable instanceof VectorDrawable) {
                                        ((VectorDrawable) drawable).setTint(vibrantColor);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        if (position == 0) {
            holder.binding.textView.setText("已播歌曲 10");
        } else if (position == 2) {
            holder.binding.textView.setText("vip  3");
        } else {
            holder.binding.textView.setText(currentItem1.getData().getName() + "  " + currentItem1.getData().getArtistsname());
        }
        holder.binding.getRoot().setOnClickListener(v -> {
            v.getContext().startActivity(new Intent(v.getContext(), MusicActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return ItemList == null ? 0 : ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMusicListBigBinding binding;

        public ViewHolder(ItemMusicListBigBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
