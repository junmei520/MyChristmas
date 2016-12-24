package com.chrismas.shiyu.mychristmas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.chrismas.shiyu.mychristmas.service.MusicService;
import com.chrismas.shiyu.mychristmas.view.GiftView;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    //礼物总个数
    private int GIFTCOUNT = 30;
    GiftView giftView = null;

    //使用handler进行消息的处理，不断进行重绘
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //重绘
                giftView.invalidate();
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //应用一进入就开启服务，启动音乐播放
        intent = new Intent(this, MusicService.class);
        intent.putExtra("action", "play");
        startService(intent);

        //产生礼物洒落效果
        giftView = (GiftView) findViewById(R.id.gift);

        // 获取当前屏幕的高宽
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        giftView.SetView(dm.heightPixels, dm.widthPixels);
        // 不断更新礼物
        update();

    }

    public void update() {
        giftView.produceGiftRandom(GIFTCOUNT);
        //发送延迟消息
        mHandler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //此处我们简洁化，当activity退出时就直接停止音乐的播放
        intent.putExtra("action", "stop");
        startService(intent);
        stopService(intent);
    }
}
