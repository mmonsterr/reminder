package com.example.reminder.control;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.reminder.R;

//声音提醒
public class MusicControl {
    private static MusicControl sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    public MusicControl(Context context) {
        this.mContext = context;
    }

    public static MusicControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MusicControl(context);
        }
        return sInstance;
    }

    //播放提醒音乐
    public void playMusic() {
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.smoke_beep);
        mMediaPlayer.start();
    }
    //关闭提醒音乐
    public void stopMusic() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.seekTo(0);
        }
    }
}
