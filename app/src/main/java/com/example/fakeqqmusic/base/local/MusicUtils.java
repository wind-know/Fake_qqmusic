package com.example.fakeqqmusic.base.local;


import static com.example.fakeqqmusic.ui.WelcomeActivity.REQUEST_PERMISSION_CODE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    public static List<Song> getMusicData(Context context) {
        List<Song> list = new ArrayList<Song>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 构建查询条件来指定路径
        String selection = MediaStore.Audio.Media.DATA + " LIKE ?";
        String[] selectionArgs = new String[] {"%/Download/%"}; // 只查找 Download 文件夹下的文件

        // 查询的列
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
        };

        // 执行查询
        Cursor cursor = context.getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER // 按照默认排序（通常是日期）
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                //歌曲
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌手
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //路径
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //时长
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                if (song.size > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (song.song.contains("-")) {
                        String[] str = song.song.split("-");
                        song.singer = str[0].trim();
                        song.song = str[1].trim();
                    }
                    list.add(song);
                }
            }

            // 释放资源
            cursor.close();
        }

        return list;
    }


    public static List<Song> getAllMusic(Context context) {
        List<Song> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE
        };
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

//        Cursor cursor = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.IS_MUSIC + "=1", null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d("TAG", "getAllMusic:DATA- "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                Log.d("TAG", "getAllMusic:ARTIST "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                Log.d("TAG", "getAllMusic: ALBUM"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                Log.d("TAG", "getAllMusic:DURATION "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                Log.d("TAG", "getAllMusic:SIZE "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                Log.d("TAG", "getAllMusic:ID "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                Log.d("TAG", "getAllMusic:TITLE "+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                Song song = new Song();
                song.setSong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                song.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                song.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));

                list.add(song);
            }
            cursor.close();
        }
        return list;
    }

}
