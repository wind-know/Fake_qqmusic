package com.example.fakeqqmusic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseLazyLoadFragment extends Fragment {

    private boolean isFirstLoad = false;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(setLayoutResourceID(), container, false);
        initView();
        isFirstLoad = true;
        if (getUserVisibleHint()) {
            onLazyLoad();
            isFirstLoad = false;
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {
            onLazyLoad();
            isFirstLoad = false;
        }
    }

    public abstract void onLazyLoad();

    public abstract int setLayoutResourceID();

    public void initView() { }
}