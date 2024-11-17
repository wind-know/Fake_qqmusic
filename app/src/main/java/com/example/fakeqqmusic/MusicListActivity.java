package com.example.fakeqqmusic;

import static com.example.fakeqqmusic.ui.MainActivity.drawableToBitmap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fakeqqmusic.base.adapter.musiclistAdapter;
import com.example.fakeqqmusic.base.adapter.starAdapter;
import com.example.fakeqqmusic.base.local.MusicListAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.base.welcome.ImmersiveStatusBarUtil;
import com.example.fakeqqmusic.databinding.ActivityMusicListBinding;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    ActivityMusicListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImmersiveStatusBarUtil.transparentBar(this, false);
        super.onCreate(savedInstanceState);
        binding = ActivityMusicListBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        initView();
    }

    void initView() {
        List<musicData> musicDatas = (List<musicData>) getIntent().getSerializableExtra("MUSIC_LIST");
        List<musicData> musicData ;
        musicData = MusicModel.getdate();
        musicData.addAll(musicDatas);
        musicData.addAll(musicDatas);
        binding.recyclerView.setAdapter(new starAdapter(musicData));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.back.setOnClickListener(v -> finish());
//        binding.toolbar.setTitle(musicDatas.get(0).getData().getName()+" || 歌单");
        binding.title.setText(musicDatas.get(0).getData().getName()+" || 歌单");
        binding.title1.setText(musicDatas.get(0).getData().getName()+" || 歌单");

        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float ratio = (float) Math.abs(verticalOffset) / totalScrollRange;
                // 限制透明度范围在0到1之间，避免出现异常值
                float alphaTitle = Math.min(1, Math.max(0, ratio));
                float alphaBottomContent = 1 - alphaTitle; // 底部内容透明度与标题相反，形成互补效果

                // 获取标题和底部内容的当前透明度
                float currentAlphaTitle = binding.title.getAlpha();
                float currentAlphaBottomContent = binding.bottomContent.getAlpha();

                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    // 当完全折叠时，隐藏底部内容，显示并完整显示标题
                    binding.bottomContent.setVisibility(View.GONE);
                    binding.title.setVisibility(View.VISIBLE);
                    binding.title.setText(musicDatas.get(0).getData().getName() + " || 歌单");
                    binding.title.setAlpha(1f);
                    binding.bottomContent.setAlpha(0f); // 完全透明
                } else {
                    // 当未完全折叠时，显示底部内容和标题，并根据滚动比例设置各自透明度，实现渐变效果
                    binding.bottomContent.setVisibility(View.VISIBLE);
                    binding.title.setVisibility(View.VISIBLE);
                    binding.title.setAlpha(alphaTitle);
                    binding.bottomContent.setAlpha(alphaBottomContent);
                }
                if (currentAlphaTitle!= alphaTitle) {
                    AlphaAnimation alphaAnimationTitle = new AlphaAnimation(currentAlphaTitle, alphaTitle);
                    alphaAnimationTitle.setDuration(0);
                    alphaAnimationTitle.setFillAfter(true);
                    binding.title.startAnimation(alphaAnimationTitle);
                }
                if (currentAlphaBottomContent!= alphaBottomContent) {
                    AlphaAnimation alphaAnimationBottomContent = new AlphaAnimation(currentAlphaBottomContent, alphaBottomContent);
                    alphaAnimationBottomContent.setDuration(0);
                    alphaAnimationBottomContent.setFillAfter(true);
                    binding.bottomContent.startAnimation(alphaAnimationBottomContent);
                }
            }
        });
        Glide.with(this)
                .load(musicDatas.get(0).getData().getPicurl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        binding.imageView.setImageDrawable(resource);
                        Bitmap bitmap = drawableToBitmap(resource);
                        // 使用 Palette 提取主色调并选择对比度明显的颜色
                        if (bitmap!= null) {
                            Palette.from(bitmap).generate(palette -> {
                                if (palette!= null) {
                                    // 获取具有足够对比度的亮色和暗色
                                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                    Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                                    Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                    int dominantColor;
                                    if (vibrantSwatch!= null) {
                                        dominantColor = vibrantSwatch.getRgb();
                                    } else if (lightVibrantSwatch!= null) {
                                        dominantColor = lightVibrantSwatch.getRgb();
                                    } else if (darkVibrantSwatch!= null) {
                                        dominantColor = darkVibrantSwatch.getRgb();
                                    } else {
                                        dominantColor = 0xFFFFFFFF ;
                                    }
                                    binding.collapsing.setContentScrimColor(dominantColor);
//                                    binding.toolbar.setTitleTextColor(adjustTextColorBasedOnBackground(dominantColor));
                                    binding.title.setTextColor(adjustTextColorBasedOnBackground(dominantColor));
                                    binding.title1.setTextColor(adjustTextColorBasedOnBackground(dominantColor));
                                }
                            });
                        }
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // 可选：处理占位图
                    }

                });

    }
    // 辅助方法：根据背景颜色动态调整文本颜色，以保证对比度
    public static int adjustTextColorBasedOnBackground(int backgroundColor) {
        int alpha = (int) (0.299 * Color.red(backgroundColor) +
                0.587 * Color.green(backgroundColor) +
                0.114 * Color.blue(backgroundColor));
        return alpha > 186? Color.BLACK : Color.WHITE;
    }
}