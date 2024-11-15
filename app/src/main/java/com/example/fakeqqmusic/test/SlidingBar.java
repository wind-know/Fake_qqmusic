package com.example.fakeqqmusic.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

public class SlidingBar extends ViewGroup {

    private float downY;
    private float upY;
    private static final float MIN_DISTANCE = 100; // 最小滑动距离
    private ViewPropertyAnimator animator;
    private boolean isShown = true;

    public SlidingBar(Context context) {
        super(context);
        animator = this.animate();
    }

    public SlidingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        animator = this.animate();
    }

    public SlidingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        animator = this.animate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                upY = event.getY();
                float distance = downY - upY;
                if (isShown && distance > MIN_DISTANCE) {
                    // 上滑，隐藏 Bar
                    hideBar();
                } else if (!isShown && -distance > MIN_DISTANCE) {
                    // 下滑，显示 Bar
                    showBar();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec); // 测量子视图
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int left = l + i * child.getMeasuredWidth();
            int top = t;
            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    private void hideBar() {
        if (isShown) {
            animator.translationY(-getHeight()).setDuration(300).withEndAction(() -> {
                isShown = false;
                setVisibility(GONE);
            }).start();
            isShown = false;
        }
    }

    private void showBar() {
        if (!isShown) {
            setVisibility(VISIBLE);
            animator.translationY(0).setDuration(300).withStartAction(() -> isShown = true).start();
            isShown = true;
        }
    }

}