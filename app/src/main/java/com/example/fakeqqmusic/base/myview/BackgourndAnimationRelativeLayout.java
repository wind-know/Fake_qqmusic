package com.example.fakeqqmusic.base.myview;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.fakeqqmusic.R;

public class BackgourndAnimationRelativeLayout extends RelativeLayout {

    private final int DURATION_ANIMATION = 500;
    private final int INDEX_BACKGROUND = 0;
    private final int INDEX_FOREGROUND = 1;

    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;

    private String musicPicUrl;

    public BackgourndAnimationRelativeLayout(Context context) {
        this(context, null);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayerDrawable();
        initObjectAnimator();
    }

    private void initLayerDrawable() {
        Drawable backgroundDrawable = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];

        /*初始化时先将前景与背景颜色设为一致*/
        drawables[INDEX_BACKGROUND] = backgroundDrawable;
        drawables[INDEX_FOREGROUND] = backgroundDrawable;

        layerDrawable = new LayerDrawable(drawables);
    }
    private float number;  // 用于存储动画进度

    public void setNumber(float number) {
        this.number = number;
        // 在这里你可以使用 number 来更新相应的属性，例如透明度
        int foregroundAlpha = (int) (number * 255);
        layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
        BackgourndAnimationRelativeLayout.this.setBackground(layerDrawable);
    }

    private void initObjectAnimator() {
        objectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1.0f);
        objectAnimator.setDuration(DURATION_ANIMATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 这里不需要再手动更新透明度，因为 setNumber 方法已经做了这件事
            }
        });
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后，更新背景
                layerDrawable.setDrawable(INDEX_BACKGROUND, layerDrawable.getDrawable(INDEX_FOREGROUND));
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void setForeground(Drawable drawable) {
        layerDrawable.setDrawable(INDEX_FOREGROUND, drawable);
    }

    public void beginAnimation() {
        objectAnimator.start();
    }
    public boolean isNeedUpdate(String musicPicUrl) {
        if (this.musicPicUrl == null || this.musicPicUrl.isEmpty()) {
            return true;
        }
        if (!musicPicUrl.equals(this.musicPicUrl)) {
            return true;
        }
        return false;
    }

}