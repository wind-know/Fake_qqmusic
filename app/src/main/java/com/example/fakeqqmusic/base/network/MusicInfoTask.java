package com.example.fakeqqmusic.base.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MusicInfoTask implements NetTask<String> {
    private static MusicInfoTask INSTANCE = null;
    private static String a = "热歌榜";
//    https://api.vore.top/api/Wyyyy?song=31654455
    private static final String HOST = "https://api.uomg.com/api/rand.music?format=json&sort="+a;

    private LoadTasksCallBack loadTasksCallBack;

    public static MusicInfoTask getInstance(){
        if (INSTANCE == null){
            INSTANCE = new MusicInfoTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String data, final LoadTasksCallBack callBack) {
        //这里展示一下传入的data数据，HOSP是写死的，没有拼接data,可以自行实现使用data重新拼接。
        Log.e("TAG", "execute: "+data);
        a=data;
        sendOkHttpRequest(HOST,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    musicData musicData = gson.fromJson(responseBody, musicData.class);
                    musicData.getData().setUrl(musicData.getData().getUrl().replace("http","https"));
                    musicData.getData().setPicurl(musicData.getData().getPicurl().replace("http","https"));
                    callBack.onSuccess(musicData);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailed();
            }
        });
    }
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}