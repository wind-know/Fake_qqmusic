package com.example.fakeqqmusic.base.network;

public interface NetTask<T> {
    void execute(T data, LoadTasksCallBack callBack);
}
