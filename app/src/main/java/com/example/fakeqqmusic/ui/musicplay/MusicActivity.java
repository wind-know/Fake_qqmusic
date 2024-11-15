package com.example.fakeqqmusic.ui.musicplay;
import static com.example.fakeqqmusic.base.myview.DiscView.DURATION_NEEDLE_ANIAMTOR;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.adapter.starAdapter;
import com.example.fakeqqmusic.base.myview.BackgourndAnimationRelativeLayout;
import com.example.fakeqqmusic.base.myview.DiscView;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.ActivityMusicBinding;
import com.example.fakeqqmusic.service.MusicService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicActivity extends AppCompatActivity implements DiscView.IPlayInfo, View.OnClickListener, MusicContract.View {

    private DiscView mDisc;
    private SeekBar mSeekBar;
    private ImageView mIvPlayOrPause, mIvNext, mIvLast,mIvchange,mIvshare, mIvlist, mIvMusicout,mIvHeart;
    private TextView mTvMusicDuration, mTvTotalMusicDuration;
    private BackgourndAnimationRelativeLayout mRootLayout;
    private TextView musicNameTextView, musicAuthorTextView;
    public static final int MUSIC_MESSAGE = 0;
    public static final String PARAM_MUSIC_LIST = "PARAM_MUSIC_LIST";
    private Handler mMusicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
            mTvMusicDuration.setText(MusicTime(mSeekBar.getProgress()));
            startUpdateSeekBarProgress();
        }
    };

    private MusicReceiver mMusicReceiver = new MusicReceiver();
    private List<musicData> mMusicDatas = new ArrayList<>();

    // ViewBinding对象
    private ActivityMusicBinding binding;
    private boolean isNo = false;
    private MusicContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicBinding.inflate(getLayoutInflater()); // 使用ViewBinding初始化
        setContentView(binding.getRoot());
        mPresenter = new MusicPresenter(this, new MusicModel());
        mPresenter.onstart();
    }

    private void initMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PLAY);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PAUSE);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_DURATION);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver, intentFilter);
    }

    private void initView() {
        mDisc = binding.discview.getRoot();
        mIvPlayOrPause = binding.ivPlayOrPause;
        mIvNext = binding.ivNext;
        mIvLast = binding.ivLast;
        mTvMusicDuration = binding.tvCurrentTime;
        mTvTotalMusicDuration = binding.tvTotalTime;
        mSeekBar = binding.musicSeekBar;
        mRootLayout = binding.rootLayout;
        musicNameTextView = binding.tvMusicName;
        musicAuthorTextView = binding.tvMusicAuthor;
        mDisc.setPlayInfoListener(this);
        mIvchange = binding.ivchange;
        mIvshare = binding.ivMusicshare;
        mIvlist = binding.ivlist;
        mIvMusicout = binding.ivMusicout;
        mIvHeart = binding.ivHeart;
        mIvchange.setOnClickListener(this);
        mIvshare.setOnClickListener(this);
        mIvlist.setOnClickListener(this);
        mIvMusicout.setOnClickListener(this);
        mIvLast.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        mIvHeart.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvMusicDuration.setText(MusicTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateSeekBarProgree();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
                startUpdateSeekBarProgress();
            }
        });

        mTvMusicDuration.setText(MusicTime(0));
        mTvTotalMusicDuration.setText(MusicTime(0));
        mDisc.setMusicDataList(mMusicDatas);
    }

    private void stopUpdateSeekBarProgree() {
        mMusicHandler.removeMessages(MUSIC_MESSAGE);
    }

    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initMusicDatas(List<musicData> musicData) {
        mMusicDatas.addAll(musicData);
        // 启动服务
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(PARAM_MUSIC_LIST, (Serializable) mMusicDatas);
        startService(intent);

    }

    private void UpdateMusicPicBackground(final String imageUrl) {
        if (mRootLayout.isNeedUpdate(imageUrl)) {
            final Activity activity = MusicActivity.this;
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(activity)
                                .load(imageUrl)
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 8))) // 模糊处理
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // 创建一个蒙版
                                                Drawable overlay = new ColorDrawable(Color.parseColor("#80FFFFFF"));
                                                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{resource, overlay});
                                                // 设置蒙版在图片上方
                                                mRootLayout.setForeground(layerDrawable);
                                                mRootLayout.beginAnimation();
                                            }
                                        });
                                    }
                                });
                    }
                }).start();
            }
        }
    }

    @Override
    public void onMusicInfoChanged(String musicName, String musicAuthor) {
        musicNameTextView.setText(musicName);
        musicAuthorTextView.setText(musicAuthor);
    }

    @Override
    public void onMusicPicChanged(String musicPicRes) {
        UpdateMusicPicBackground(musicPicRes);
    }

    @Override
    public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {
        switch (musicChangedStatus) {
            case PLAY:
                play();
                break;
            case PAUSE:
                pause();
                break;
            case NEXT:
                next();
                break;
            case LAST:
                last();
                break;
            case STOP:
                stop();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mIvPlayOrPause) {
            mDisc.playOrPause();
        } else if (v == mIvNext) {
            mDisc.next();
        } else if (v == mIvLast) {
            mDisc.last();
        }else if (v == mIvlist){
            onClickDialog(v);
        }else if (v == mIvchange){
            Toast.makeText(this, "切换播放模式", Toast.LENGTH_SHORT).show();
        }else if (v == mIvshare){
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
        }else if (v == mIvMusicout){
            finish();
        }else if (v == mIvHeart) {
            if(isNo){
                mIvHeart.setImageResource(R.drawable.icon_nice_no);
                isNo = !isNo;
                Log.d("TAG", "NO");
            }
            else{
                mIvHeart.setImageResource(R.drawable.icon_nice_red);
                isNo = !isNo;
            }
        }

    }
    public void onClickDialog(View view) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View layout = getLayoutInflater().inflate(R.layout.bottomsheet_playlist, null);
        starAdapter adapter = new starAdapter(mMusicDatas);
        RecyclerView recyclerView = layout.findViewById(R.id.dialog_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
//        adapter.openLoadAnimation();
        mBottomSheetDialog.setContentView(layout);
        mBottomSheetDialog.show();
    }
    private void play() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PLAY);
        startUpdateSeekBarProgress();
    }

    private void pause() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PAUSE);
        stopUpdateSeekBarProgree();
    }

    private void stop() {
        stopUpdateSeekBarProgree();
        mIvPlayOrPause.setImageResource(R.drawable.icon_play);
        mTvMusicDuration.setText(MusicTime(0));
        mTvTotalMusicDuration.setText(MusicTime(0));
        mSeekBar.setProgress(0);
    }

    private void next() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_NEXT);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgree();
        mTvMusicDuration.setText(MusicTime(0));
        mTvTotalMusicDuration.setText(MusicTime(0));
    }

    private void last() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_LAST);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgree();
        mTvMusicDuration.setText(MusicTime(0));
        mTvTotalMusicDuration.setText(MusicTime(0));
    }

    private void complete(boolean isOver) {
        if (isOver) {
            mDisc.stop();
        } else {
            mDisc.next();
        }
    }

    private void optMusic(final String action) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }

    private void seekTo(int position) {
        Intent intent = new Intent(MusicService.ACTION_OPT_MUSIC_SEEK_TO);
        intent.putExtra(MusicService.PARAM_MUSIC_SEEK_TO, position);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startUpdateSeekBarProgress() {
        stopUpdateSeekBarProgree();
        mMusicHandler.sendEmptyMessageDelayed(0, 1000);
    }

    private String MusicTime(int duration) {
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        return (min < 10 ? "0" + min : min + "") + ":" + (sec < 10 ? "0" + sec : sec + "");
    }

    private void updateMusicDurationInfo(int totalDuration) {
        mSeekBar.setProgress(0);
        mSeekBar.setMax(totalDuration);
        mTvTotalMusicDuration.setText(MusicTime(totalDuration));
        mTvMusicDuration.setText(MusicTime(0));
        startUpdateSeekBarProgress();
    }

    @Override
    public void setPresenter(MusicContract.Presenter presenter) {
         mPresenter = presenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public void setMusicData(List<musicData> musicData) {
        initMusicDatas(musicData);
        initView();
        initMusicReceiver();
        makeStatusBarTransparent();
    }

    @Override
    public Boolean isACtive() {
        return !isFinishing();
    }

    class MusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicService.ACTION_STATUS_MUSIC_PLAY)) {
                mIvPlayOrPause.setImageResource(R.drawable.icon_noplay);
                int currentPosition = intent.getIntExtra(MusicService.PARAM_MUSIC_CURRENT_POSITION, 0);
                mSeekBar.setProgress(currentPosition);
                if (!mDisc.isPlaying()) {
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_PAUSE)) {
                mIvPlayOrPause.setImageResource(R.drawable.icon_playa);
                if (mDisc.isPlaying()) {
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_DURATION)) {
                int duration = intent.getIntExtra(MusicService.PARAM_MUSIC_DURATION, 0);
                updateMusicDurationInfo(duration);
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_COMPLETE)) {
                boolean isOver = intent.getBooleanExtra(MusicService.PARAM_MUSIC_IS_OVER, true);
                complete(isOver);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);
        mPresenter.unSubscribe();
        mPresenter = null;
    }
}