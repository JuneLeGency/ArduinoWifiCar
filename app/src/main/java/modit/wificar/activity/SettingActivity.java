package modit.wificar.activity;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import modit.wificar.R;
import modit.wificar.common.Util;
import modit.wificar.config.CarConfig_;

/**
 * Created by legency on 2015/3/31.
 */
@EActivity(R.layout.setting)
public class SettingActivity extends Activity{
    @Pref
    CarConfig_ carConfig_;

    @ViewById
    EditText wifi_ssid;

    @ViewById
    EditText ip;
    @ViewById
    EditText port_video;
    @ViewById
    EditText port_control;
    @ViewById
    EditText up;
    @ViewById
    EditText down;
    @ViewById
    EditText left;
    @ViewById
    EditText right;

    String default_ip="192.168.8.1";
    String default_ssid="hjwifi2014";
    String default_port_video="8083";
    String default_port_control="2001";
    String default_up="a";
    String default_down="b";
    String default_left="d";
    String default_right="c";

    @ViewById
    TextView video_preview;


    @AfterViews
    void bindView(){
//        wifi_ssid.setText(carConfig_.wifi_ssid().getOr(default_ssid));
//        ip.setText(carConfig_.ip().getOr(default_ip));
//        port_video.setText(carConfig_.port_video().getOr(default_port_video));
//        port_control.setText(carConfig_.port_control().getOr(default_port_control));
//        up.setText(carConfig_.up().getOr(default_up));
//        down.setText(carConfig_.down().getOr(default_down));
//        left.setText(carConfig_.left().getOr(default_left));
//        right.setText(carConfig_.right().getOr(default_right));
        wifi_ssid.setText(carConfig_.wifi_ssid().get());
        ip.setText(carConfig_.ip().get());
        port_video.setText(carConfig_.port_video().get());
        port_control.setText(carConfig_.port_control().get());
        up.setText(carConfig_.up().get());
        down.setText(carConfig_.down().get());
        left.setText(carConfig_.left().get());
        right.setText(carConfig_.right().get());
    }

    @TextChange({R.id.ip, R.id.port_video})
    void onTextChangesOnSomeTextViews(TextView tv, CharSequence text){
        String url=Util.getVideoUrl(ip.getText().toString(), port_video.getText().toString());
        video_preview.setText(url);
    }

    @Click
    void saveClicked(){
        carConfig_.edit().wifi_ssid().put(wifi_ssid.getText().toString())
                .ip().put(ip.getText().toString())
                .port_video().put(port_video.getText().toString())
                .port_control().put(port_control.getText().toString())
                .up().put(up.getText().toString())
                .down().put(down.getText().toString())
                .left().put(left.getText().toString())
                .right().put(right.getText().toString()).apply();
        finish();
    }
}
