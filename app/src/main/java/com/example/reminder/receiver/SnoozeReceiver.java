package com.example.reminder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.reminder.control.MusicControl;

public class SnoozeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase("com.akaxiaochang.SnoozeReceiver")) {
//        Bundle b1=intent.getExtras();
//        assert b1 != null;
//        MediaPlayer mediaPlayer=b1.getParcelable("AlarmSongSnooze");
//        assert mediaPlayer != null;
//        mediaPlayer.stop();
            //停止音乐提醒
            System.out.println("hello from snooze receive");
            MusicControl.getInstance(context).stopMusic();
        }
    }
}
