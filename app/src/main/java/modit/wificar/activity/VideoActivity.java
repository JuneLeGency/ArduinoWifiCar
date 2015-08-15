package modit.wificar.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.ui.widget.DanmakuView;
import modit.wificar.R;
import modit.wificar.common.Util;
import modit.wificar.config.CarConfig_;
import modit.wificar.connection.Control;
import modit.wificar.view.MySurfaceView;

/**
 * Created by legency on 2015/4/2.
 */
@EActivity(R.layout.video_layout)
public class VideoActivity extends Activity{

    @ViewById
    MySurfaceView video_view;

    @Bean
    Control control;

    @ViewById
    View touch_layer;

    @ViewById
    ImageButton up;

    @ViewById
    ImageButton down;

    @ViewById
    ImageButton left;

    @ViewById
    ImageButton right;

    @ViewById
    DanmakuView sv_danmaku;

    @Pref
    CarConfig_ carConfig_;

    View.OnTouchListener l = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String commend = "";
            if(v==up){
                commend=carConfig_.up().get();
            }else if (v==down){
                commend=carConfig_.down().get();
            }else if (v==left){
                commend=carConfig_.left().get();
            }else if (v==right){
                commend=carConfig_.right().get();
            }

            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    control.send(commend);
                    break;
                case MotionEvent.ACTION_UP:
                    control.send("e");
                default:
                    break;
            }
//            addDanmaku(true);
            return false;
        }
    };



    private void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = DanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = "这是一条弹幕" + System.nanoTime();
        danmaku.padding = 5;
        danmaku.priority = 1;
        danmaku.isLive = islive;
        danmaku.time = sv_danmaku.getCurrentTime() + 1200;
        danmaku.textSize = 25f;
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        //danmaku.underlineColor = Color.GREEN;
        danmaku.borderColor = Color.GREEN;
        sv_danmaku.addDanmaku(danmaku);
    }

    @AfterViews
    void init_Views(){
        video_view.GetCameraIP(Util.getVideoUrl(carConfig_));
        control.connect();
        up.setOnTouchListener(l);
        down.setOnTouchListener(l);
        left.setOnTouchListener(l);
        right.setOnTouchListener(l);
    }
}
