package com.example.fakeqqmusic.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//public class BackgroundSoundService extends Service {
//    private static final String TAG = BackgroundSoundService.class.getSimpleName();
//    private MediaPlayer player; // 用于播放音乐的MediaPlayer实例
//    private AudioManager audioManager; // 用于管理音频焦点
//    private AudioFocusListener audioFocusListener; // 音频焦点变化的监听器
//    private boolean isPlaying = false; // 跟踪音乐是否正在播放
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        // 服务不提供绑定功能，返回null
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        initializeMediaPlayer(); // 初始化MediaPlayer
//        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE); // 获取AudioManager实例
//        audioFocusListener = new AudioFocusListener(); // 初始化音频焦点监听器
//        requestAudioFocus(); // 请求音频焦点
//    }
//
//    private void initializeMediaPlayer() {
//        // 初始化MediaPlayer，播放指定资源文件中的音乐
//        try {
////            player = MediaPlayer.create(this, R.raw.idil); // 确保idil是正确的资源文件名
//            player.setLooping(true); // 设置音乐循环播放
//            player.setVolume(1.0f, 1.0f); // 设置音量为100%
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // 当服务收到启动命令时，开始播放音乐
//        if (player != null && !isPlaying) {
//            player.start();
//            isPlaying = true;
//        }
//        return START_STICKY; // 返回START_STICKY以确保服务在系统杀死后能够重新创建
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // 服务销毁时，释放MediaPlayer资源
//        if (player != null) {
//            player.stop();
//            player.release();
//            player = null;
//        }
//        abandonAudioFocus(); // 放弃音频焦点
//    }
//
//    private void requestAudioFocus() {
//        // 请求音频焦点，以便在其他应用需要音频时暂停或停止播放
//        audioManager.requestAudioFocus(audioFocusListener,
//                AudioManager.STREAM_MUSIC,
//                AudioManager.AUDIOFOCUS_GAIN);
//    }
//
//    private void abandonAudioFocus() {
//        // 放弃音频焦点
//        if (audioManager != null) {
//            audioManager.abandonAudioFocus(audioFocusListener);
//        }
//    }
//
//    private class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {
//        @Override
//        public void onAudioFocusChange(int focusChange) {
//            // 处理音频焦点变化
//            switch (focusChange) {
//                case AudioManager.AUDIOFOCUS_GAIN:
//                    // 获得音频焦点时，恢复播放
//                    if (!isPlaying && player != null) {
//                        player.start();
//                        isPlaying = true;
//                    }
//                    break;
//                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
//                    // 临时失去音频焦点时，暂停播放
//                    if (isPlaying && player != null) {
//                        player.pause();
//                        isPlaying = false;
//                    }
//                    break;
//                case AudioManager.AUDIOFOCUS_LOSS:
//                    // 永久失去音频焦点时，停止播放并释放资源
//                    if (isPlaying && player != null) {
//                        player.stop();
//                        player.release();
//                        player = null;
//                        isPlaying = false;
//                    }
//                    break;
//                case AudioManager.AUDIOFOCUS_NONE:
//                    // 没有音频焦点，不进行操作
//                    break;
//            }
//        }
//    }
//}
