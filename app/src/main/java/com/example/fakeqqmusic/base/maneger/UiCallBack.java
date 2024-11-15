package com.example.fakeqqmusic.base.maneger;

import android.os.Bundle;

public interface UiCallBack {

    //初始化savedInstanceState

    void initBeforeView(Bundle savedInstanceState);

//初始化数据 相当于onCreate

    void initData(Bundle savedInstanceState);

    int getLayoutId();
}

