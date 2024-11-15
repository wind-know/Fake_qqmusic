package com.example.fakeqqmusic.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.viewpager2.widget.ViewPager2;

public class NestedScrollableHost extends FrameLayout {
    private int touchSlop;
    private float initialX;
    private float initialY;
    private ViewPager2 parentViewPager;

    public NestedScrollableHost(Context context) {
        super(context);
        init();
    }

    public NestedScrollableHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private View getChild() {
        return getChildCount() > 0 ? getChildAt(0) : null;
    }

//    private ViewPager2 getParentViewPager() {
//        View v = (View) getParent();
//        while (v != null && !(v instanceof ViewPager2)) {
//            v = (View) v.getParent();
//        }
//        return (ViewPager2) v;
//    }
    private ViewPager2 getParentViewPager() {
        View v = (View) getParent();
        while (v != null) {
            if (v instanceof ViewPager2) {
                return (ViewPager2) v;
            }
            // 尝试获取父视图的父视图
            v = (v.getParent() instanceof View) ? (View) v.getParent() : null;
        }
        return null; // 如果没有找到 ViewPager2，返回 null
    }
    private boolean canChildScroll(int orientation, float delta) {
        View child = getChild();
        if (child == null) {
            return false;
        }
        switch (orientation) {
            case ViewPager2.ORIENTATION_HORIZONTAL:
                return child.canScrollHorizontally((int) -delta);
            case ViewPager2.ORIENTATION_VERTICAL:
                return child.canScrollVertically((int) -delta);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        handleInterceptTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    //    private void handleInterceptTouchEvent(MotionEvent e) {
//        ViewPager2 viewPager2 = getParentViewPager();
//        if (viewPager2 == null) {
//            return;
//        }
//        int orientation = viewPager2.getOrientation();
//
//        if (e.getAction() == MotionEvent.ACTION_DOWN) {
//            initialX = e.getX();
//            initialY = e.getY();
//            requestDisallowInterceptTouchEvent(true);
//        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
//            float dx = e.getX() - initialX;
//            float dy = e.getY() - initialY;
//            boolean isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL;
//
//            float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5f : 1f);
//            float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1f : 0.5f);
//
//            if (scaledDx > touchSlop || scaledDy > touchSlop) {
//                if (isVpHorizontal == (scaledDy > scaledDx)) {
//                    requestDisallowInterceptTouchEvent(false);
//                } else {
//                    if (canChildScroll(orientation, isVpHorizontal ? dx : dy)) {
//                        requestDisallowInterceptTouchEvent(true);
//                    } else {
//                        requestDisallowInterceptTouchEvent(false);
//                    }
//                }
//            }
//        }
//    }
    private void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 viewPager2 = getParentViewPager();
        if (viewPager2 == null) {
            return;
        }
        int orientation = viewPager2.getOrientation();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            initialX = e.getX();
            initialY = e.getY();
            requestDisallowInterceptTouchEvent(false); // 允许父视图拦截事件
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = e.getX() - initialX;
            float dy = e.getY() - initialY;
            boolean isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL;

            float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5f : 1f);
            float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1f : 0.5f);

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal && scaledDx > scaledDy) {
                    // 如果是横向滑动，且横向距离大于纵向距离
                    requestDisallowInterceptTouchEvent(false); // 允许父视图拦截事件
                } else if (!isVpHorizontal && scaledDy > scaledDx) {
                    // 如果是纵向滑动，且纵向距离大于横向距离
                    requestDisallowInterceptTouchEvent(false); // 允许父视图拦截事件
                } else {
                    // 其他情况，禁止父视图拦截事件
                    requestDisallowInterceptTouchEvent(true);
                }
            }
        }
    }
}