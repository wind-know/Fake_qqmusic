package com.example.fakeqqmusic.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.MusicInfoTask;

public class MusicApiViewModel extends ViewModel {
    private MutableLiveData<String> searchDefaultLiveData = new MutableLiveData<>();

    public LiveData<String> getSearchDefault() {
        // 使用MusicInfoTask发送网络请求
        MusicInfoTask.getInstance().execute("", new LoadTasksCallBack() {

            @Override
            public void onSuccess(Object data) {
                searchDefaultLiveData.postValue(data.toString()); // 假设您想将musicData对象转换为字符串

            }
            @Override
            public void onFailed() {
                // 处理失败情况
                searchDefaultLiveData.postValue("Error: Network request failed");
            }
        });
        return searchDefaultLiveData;
    }
}