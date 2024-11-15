package com.example.fakeqqmusic.ui.musicplay;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;

import com.example.fakeqqmusic.base.network.musicData;


import java.util.ArrayList;
import java.util.List;

public class MusicModel implements MusicContract.Model{
    public static List<musicData> getdate(){
        List<musicData> mMusicDatas = new ArrayList<>();
        musicData musicData1 = new musicData("APT.", "ROSÉ", "https://music.163.com/song/media/outer/url?id=2637558926", "https://p1.music.126.net/t47xJ6AwOv9qOQ51PZoiPw==/109951170052324935.jpg");
        musicData musicData2 = new musicData("Ayrilip", "Jalal Anwar", "http://music.163.com/song/media/outer/url?id=2635669576", "https://p1.music.126.net/_bCh7toM6AkVn0Inn7Bv4w==/109951170036005143.jpg");
        musicData musicData3 = new musicData("如果月亮会说话", "王心凌", "https://music.163.com/song/media/outer/url?id=2631167125", "https://p1.music.126.net/6XysFYqLIfFJ2Xb-OCagdw==/109951169994296407.jpg");
        musicData musicData4 = new musicData("Soft Spot (Acoustic)", "keshi", "http://music.163.com/song/media/outer/url?id=2639918917", "https://p1.music.126.net/-_EZ6cZnvzmeZnR_KNOAqQ==/109951170073562478.jpg");
        musicData musicData5 = new musicData("Emotional Love", "Brawna", "https://music.163.com/song/media/outer/url?id=2636992790", "https://p1.music.126.net/Jz-VMVvybsYHMGtDddOXIQ==/109951170047896912.jpg");
        musicData musicData6 = new musicData("三环路", "SAM", "https://music.163.com/song/media/outer/url?id=2638790383", "https://p1.music.126.net/RO98Z5v7W_3IfTk89Pp4kQ==/109951170063845401.jpg");
        musicData musicData7 = new musicData("落霞挽霓虹", "邹沛沛", "https://music.163.com/song/media/outer/url?id=2640301964", "https://p1.music.126.net/BJDJs5Io0Z4VrePNm8jTPg==/109951170078096472.jpg");
        mMusicDatas.add(musicData1);
        mMusicDatas.add(musicData2);
        mMusicDatas.add(musicData3);
        mMusicDatas.add(musicData4);
        mMusicDatas.add(musicData5);
        mMusicDatas.add(musicData6);
        mMusicDatas.add(musicData7);
        return mMusicDatas;
    }

    @Override
    public void getMusicInfo(String ip, LoadTasksCallBack callBack) {
        callBack.onSuccess(getdate());
    }
}