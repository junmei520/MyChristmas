package com.chrismas.shiyu.mychristmas.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import com.chrismas.shiyu.mychristmas.R;

import static android.R.attr.action;

/**
 * 用于播放音乐的服务
 */
public class MusicService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    MediaPlayer player;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        if("play".equals(action)) {
            //播放
            play();
        }else if("stop".equals(action)){
            //停止
            stop();
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void stop() {
        if (player != null) {
            player.stop();
            player.reset();
            player.release();//释放加载的文件
            player = null;//不要忘了！
        }
    }

    private void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.jinglebells);
            player.setLooping(true);

        }
        if (player != null && !player.isPlaying()) {
            player.start();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();//停止音乐
    }
}
