package modit.wificar.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import modit.wificar.R;
import modit.wificar.common.Util;
import modit.wificar.config.CarConfig_;
import modit.wificar.connection.Control;

/**
 * Created by legency on 2015/4/3.
 */
@EActivity(R.layout.webview)
public class WebViewActivity extends Activity {
    @ViewById
    WebView webView;

    @ViewById
    ImageButton up;

    @ViewById
    ImageButton down;

    @ViewById
    ImageButton left;

    @ViewById
    ImageButton right;

    @Pref
    CarConfig_ carConfig_;

    @Bean
    Control control;

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
            return false;
        }
    };

    @Click
    void settingClicked(){
        Intent i =new Intent(this,SettingActivity_.class);
        startActivity(i);
    }

    @AfterViews
    void bind(){
        String data="<html><head></head><body style=\"margin: 0px;\"><img id=\"img1\" src=\""+ Util.getVideoStreamUrl(carConfig_)+"\" style=\"width: 100%; height: 100%;\"></body></html>";
        webView.loadData(data,"text/html", "utf-8");
        control.initUrl();
        control.connect();
        up.setOnTouchListener(l);
        down.setOnTouchListener(l);
        left.setOnTouchListener(l);
        right.setOnTouchListener(l);
    }
}