package com.example.fakeqqmusic.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.welcome.GetTipsDialog;
import com.example.fakeqqmusic.base.welcome.ImmersiveStatusBarUtil;
import com.example.fakeqqmusic.base.myview.TextCircleView;
import com.example.fakeqqmusic.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    /*动态申请读、写权限*/
    public static final int REQUEST_PERMISSION_CODE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RelativeLayout mUIRootLayout;
    private TextCircleView tv_time;
    private TextView tv_line;
    private long time = 2077;
    private boolean isHideNavigation = false, isStartAnimation = false;
    private Animation mShowAnimation;
    private ActivityWelcomeBinding binding;
    private CountDownTimer mDownTimer = new CountDownTimer(time, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (!isStartAnimation) {
                tv_line.setVisibility(View.VISIBLE);
                tv_line.setAnimation(mShowAnimation);
                isStartAnimation = true;
            }
            if (millisUntilFinished >= 1000) {
                String time = String.valueOf(millisUntilFinished / 1000);
                tv_time.setText(time);
                //开始淡出动画
                if (millisUntilFinished < 1900) {
                    Log.d("Welcome", "onTick: 是时候隐藏状态栏了！");
                    Window window = getWindow();
                    if (window != null) {
                        if (!isHideNavigation) isHideNavigation = true;
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    }
                }
            }
        }

        @Override
        public void onFinish() {
            startMainActivity();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ImmersiveStatusBarUtil.transparentBar(this, false);

        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mUIRootLayout = binding.welcomeActivityBottomLayout;
        mUIRootLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                if (insets.getSystemWindowInsetBottom() < 288 && !isHideNavigation) {
                    int paddingBottom = insets.getSystemWindowInsetBottom();//给予一个最低padding高度
                    v.setPadding(v.getPaddingRight(), v.getPaddingTop(), v.getPaddingLeft(), paddingBottom < 30 ? 30 : paddingBottom);
                }
                return insets;
            }
        });
        tv_time = binding.welcomeActivityTopTvTimer;
        tv_line = binding.welcomeActivityTopTvSky;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mShowAnimation == null)
            mShowAnimation = AnimationUtils.loadAnimation(this, R.anim.view_gradually_show);
        if (!isGetPermission()) {
            GetTipsDialog tipsDialog = new GetTipsDialog(this, R.style.DialogTheme);
            tipsDialog.setCancelable(false);
            tipsDialog.show();
            tipsDialog.setOnChooseListener(new GetTipsDialog.OnChooseListener() {
                @Override
                public void onChoose(boolean result) {
                    if (result) {
                        getStorage();
                    } else finish();
                }
            });
        } else mDownTimer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownTimer != null) {
            mDownTimer.cancel();
            mDownTimer = null;
        }
        if (mShowAnimation != null) {
            mShowAnimation.reset();
            mShowAnimation.cancel();
            mShowAnimation = null;
        }
        if (tv_line != null) tv_line = null;
        if (tv_time != null) tv_time = null;
        if (mUIRootLayout != null) mUIRootLayout = null;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("welcome", "onRequestPermissionsResult: 确认动态获取读写权限");
                mDownTimer.start();
            } else {
                finish();
            }
        }
    }

    private void getStorage() {
        /*动态获取存储权限的方法*/
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //判断Android版本是否大于6.0 || 在API(26)以后规定必须要动态获取权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

    }

    private boolean isGetPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED; //判断是否已获取权限;
    }

    private void startMainActivity() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        //关闭当前页面
        finish();
    }
}
