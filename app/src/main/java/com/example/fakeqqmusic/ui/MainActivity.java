package com.example.fakeqqmusic.ui;

import static com.example.fakeqqmusic.ui.musicplay.MusicActivity.PARAM_MUSIC_LIST;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.request.target.SimpleTarget;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.adapter.starAdapter;
import com.example.fakeqqmusic.base.myview.DiscView;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.base.welcome.ImmersiveStatusBarUtil;
import com.example.fakeqqmusic.databinding.ActivityMainBinding;
import com.example.fakeqqmusic.databinding.MyplayitemBinding;
import com.example.fakeqqmusic.service.MusicService;
import com.example.fakeqqmusic.test.MusicApiViewModel;
import com.example.fakeqqmusic.ui.fragment.dashboard.DashboardFragment;
import com.example.fakeqqmusic.ui.fragment.dashboard.DashboardModel;
import com.example.fakeqqmusic.ui.fragment.dashboard.DashboardPresenter;
import com.example.fakeqqmusic.ui.fragment.home.HomeFragment;
import com.example.fakeqqmusic.ui.fragment.home.HomeModel;
import com.example.fakeqqmusic.ui.fragment.home.HomePresenter;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneFragment;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneModel;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZonePresenter;
import com.example.fakeqqmusic.ui.fragment.star.starFragment;
import com.example.fakeqqmusic.ui.fragment.star.starModel;
import com.example.fakeqqmusic.ui.fragment.star.starPresenter;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;
import com.example.fakeqqmusic.ui.musicplay.MusicModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.permissionx.guolindev.PermissionX;

import java.io.Serializable;
import java.util.List;
public class MainActivity extends AppCompatActivity  implements DiscView.IPlayInfo {
    private BottomNavigationView bottomNavigation;
    HomeFragment FirstFragment;
    private ActivityMainBinding binding;
    private MusicApiViewModel musicApiViewModel;
    private View playerView;
    MyplayitemBinding playitembinding ;
    private ImageView playlist;
    private ImageView musicPic;
    private ImageView playPauseButton;
    private List<musicData> mMusicDatas;
    private TextView songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImmersiveStatusBarUtil.transparentBar(this, false);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        //window
        playitembinding = MyplayitemBinding.inflate(getLayoutInflater());

        initView();
        initListener();

    }

    private void initService() {

        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(PARAM_MUSIC_LIST, (Serializable) mMusicDatas);
        startService(intent);

    }



    public void initView() {
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment fragment = (HomeFragment) fm.findFragmentById(R.id.nav_host_fragment_activity_main);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment newFragment = HomeFragment.newInstance();
            HomePresenter presenter = new HomePresenter(newFragment, new HomeModel());
            FirstFragment = newFragment;
            ft.add(R.id.nav_host_fragment_activity_main, newFragment);
            ft.commit();
        }

        bottomNavigation = binding.navView;
        mMusicDatas= MusicModel.getdate();
        initMusicReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionX.init(this)
                .permissions(Manifest.permission.SYSTEM_ALERT_WINDOW) // 悬浮窗权限
                .onExplainRequestReason((scope, deniedList) -> {
                    // 向用户解释为什么需要这些权限
                    scope.showRequestReasonDialog(deniedList, "为了显示播放器悬浮窗，请允许悬浮窗权限", "我明白了");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    // 提示用户去设置中打开权限
                    scope.showForwardToSettingsDialog(deniedList, "请手动打开悬浮窗权限", "我知道了");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        addWindow();
                    } else {
                        if (!deniedList.isEmpty()) {
                            Toast.makeText(this, "悬浮窗权限未授予，无法显示悬浮窗。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void initMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PLAY);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PAUSE);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_DURATION);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver, intentFilter);
    }
    public void initListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (item.getItemId() == R.id.navigation_home) {
                if (!FirstFragment.isACtive()) {
                    HomeFragment newFragment = HomeFragment.newInstance();
                    HomePresenter presenter = new HomePresenter(newFragment, new HomeModel());
                    ft.replace(R.id.nav_host_fragment_activity_main, newFragment);
                } else {
                    ft.replace(R.id.nav_host_fragment_activity_main, FirstFragment);
                }
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                DashboardFragment newFragment = DashboardFragment.newInstance();
                DashboardPresenter presenter = new DashboardPresenter(newFragment, new DashboardModel());
                ft.replace(R.id.nav_host_fragment_activity_main, newFragment);
            } else if (item.getItemId() == R.id.navigation_Myzone) {
                MyZoneFragment newsFragment = MyZoneFragment.newInstance();
                MyZonePresenter presenter = new MyZonePresenter(newsFragment, new MyZoneModel());
                ft.replace(R.id.nav_host_fragment_activity_main, newsFragment);
            } else if (item.getItemId() == R.id.navigation_my) {
                starFragment newsFragment = new starFragment();
                starPresenter presenter = new starPresenter(newsFragment, new starModel());
                ft.replace(R.id.nav_host_fragment_activity_main, newsFragment);
            }
            ft.commit();
            return true;
        });
        setKeyboardVisibilityListener(findViewById(android.R.id.content), new OnKeyboardVisibilityListener() {
            @Override
            public void onKeyboardShow() {
                if (playerView.isAttachedToWindow()) {
                    playerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onKeyboardHide() {
                playerView.setVisibility(View.VISIBLE);
            }
        });
    }
    private boolean isPlayerViewAdded = false;
    private void addWindow() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        playerView = LayoutInflater.from(this).inflate(R.layout.myplayitem, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ;
//        加入这一句可以全局悬浮

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;


        int leftPadding = getResources().getDimensionPixelSize(R.dimen.padding);
        int rightPadding = getResources().getDimensionPixelSize(R.dimen.padding);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        layoutParams.width = screenWidth - leftPadding - rightPadding;


        int navBarHeight = getNavBarHeight()+30;
        layoutParams.y = navBarHeight;
        if(!isPlayerViewAdded) {
            windowManager.addView(playerView, layoutParams);
            isPlayerViewAdded = true;
        }
        playlist = playerView.findViewById(R.id.playlist);
        musicPic = playerView.findViewById(R.id.music_pic);
        songTitle = playerView.findViewById(R.id.songTitle);
        playPauseButton = playerView.findViewById(R.id.playPauseButton);
        playPauseButton.setTag(R.drawable.icon_playa);
        playlist.setOnClickListener(v -> {
            onClickDialog(v);
        });

        playPauseButton.setOnClickListener(v -> {
            Log.d("ButtonClick", "Play/Pause button clicked");
            int currentDrawableId = (int) playPauseButton.getTag();

            if (currentDrawableId != R.drawable.icon_playa) {
                // 如果正在播放，发送暂停命令
                Intent pauseIntent = new Intent(MusicService.ACTION_OPT_MUSIC_PAUSE);
                playPauseButton.setImageResource(R.drawable.icon_playa);
                playPauseButton.setTag(R.drawable.icon_playa);
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(pauseIntent);
            } else if(currentDrawableId == R.drawable.icon_playa){
                initService();
                // 如果暂停，发送播放命令
                Log.d("ButtonClick", "P播放命令");
                Intent playIntent = new Intent(MusicService.ACTION_OPT_MUSIC_PLAY);
                playPauseButton.setImageResource(R.drawable.icon_noplay);
                playPauseButton.setTag(R.drawable.icon_noplay);
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(playIntent);
            }
        });
        musicPic.setOnClickListener(v -> {
            Toast.makeText(this, "点击了悬浮窗", Toast.LENGTH_SHORT).show();
            playerView.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, MusicActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }

    private int getNavBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        playerView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy(){
        if (playerView.isAttachedToWindow()) {
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            windowManager.removeView(playerView);
        }
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);
    }
    public void onClickDialog(View view) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View layout = getLayoutInflater().inflate(R.layout.bottomsheet_playlist, null);
        starAdapter adapter = new starAdapter(mMusicDatas);
        RecyclerView recyclerView = layout.findViewById(R.id.dialog_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        mBottomSheetDialog.setContentView(layout);
        mBottomSheetDialog.show();
    }
    private MusicReceiver mMusicReceiver = new MusicReceiver();

    @Override
    public void onMusicInfoChanged(String musicName, String musicAuthor) {
        TextView musicNameTextView = playerView.findViewById(R.id.songTitle);
        musicNameTextView.setText(musicName+" - "+musicAuthor);

    }
    public static void setKeyboardVisibilityListener(final View rootView, final OnKeyboardVisibilityListener listener) {
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = rootView.getHeight();
                int keypadHeight = screenHeight - rect.bottom;
                if (keypadHeight > screenHeight * 0.15) { // If keyboard is more than 15% of the screen height, consider it open
                    listener.onKeyboardShow();
                } else {
                    listener.onKeyboardHide();
                }
                return true;
            }
        });
    }
    public interface OnKeyboardVisibilityListener {
        void onKeyboardShow();
        void onKeyboardHide();
    }
    @Override
    public void onMusicPicChanged(String musicPicRes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.with(playerView)
                        .load(musicPicRes)
                        .into(musicPic);
            }
        }).start();
    }

    @Override
    public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {

    }
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    class MusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicService.ACTION_STATUS_MUSIC_PLAY)) {
                playPauseButton.setImageResource(R.drawable.icon_noplay);
                playPauseButton.setTag(R.drawable.icon_noplay);
                int currentindex = intent.getIntExtra(MusicService.PARAM_MUSIC_NOW_MUSIC_INDEX, 0);
                Glide.with(context) // 获取图片的URL
                        .load(mMusicDatas.get(currentindex).getData().getPicurl()) // 加载图片
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                musicPic.setImageDrawable(resource);

                                // 将 Drawable 转换为 Bitmap
                                Bitmap bitmap = drawableToBitmap(resource);
                                // 使用 Palette 提取主色调
                                if (bitmap != null) {
                                    Palette.from(bitmap).generate(palette -> {
                                        if (palette != null) {
                                            // 提取主色调
                                            int dominantColor = palette.getDominantColor(0xFF000000); // 默认黑色
                                            // 设置背景颜色
                                            playerView.setBackgroundColor(dominantColor);
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                // 可选：处理占位图
                            }

                        });
                songTitle.setText(mMusicDatas.get(currentindex).getData().getName()+" - "+mMusicDatas.get(currentindex).getData().getArtistsname());


            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_PAUSE)) {
                playPauseButton.setImageResource(R.drawable.icon_playa);
                playPauseButton.setTag(R.drawable.icon_playa);
            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_DURATION)) {

            } else if (action.equals(MusicService.ACTION_STATUS_MUSIC_COMPLETE)) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(MusicService.ACTION_OPT_MUSIC_NEXT));
            }

        }

    }
}