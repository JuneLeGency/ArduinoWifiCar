package modit.wificar.common;

import android.util.Log;

import modit.wificar.config.CarConfig_;

/**
 * Created by legency on 2015/4/4.
 */
public class Util {
    public static String getVideoUrl(String ip, String port) {
        return "http://" + ip + ":" + port + "/?action=snapshot";
    }

    public static String getVideoStreamUrl(String ip, String port) {
        return "http://" + ip + ":" + port + "/?action=stream";
    }

    public static String getVideoUrl(CarConfig_ carConfig_) {
        return getVideoUrl(carConfig_.ip().get(), carConfig_.port_video().get());
    }

    public static String getVideoStreamUrl(CarConfig_ carConfig_) {
        String url = getVideoStreamUrl(carConfig_.ip().get(), carConfig_.port_video().get());
        Log.i("video url used", url);
        return url;
    }
}
