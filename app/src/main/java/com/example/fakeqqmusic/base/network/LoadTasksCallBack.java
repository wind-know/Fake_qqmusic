package com.example.fakeqqmusic.base.network;

public interface LoadTasksCallBack<T> {
    void onSuccess(T data);
    void onFailed();
}
