package modit.wificar.config;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by legency on 2015/4/4.
 */

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface CarConfig {

    @DefaultString("192.168.1.1")
    String ip();

    @DefaultString("8080")
    String port_video();

    @DefaultString("2001")
    String port_control();

    @DefaultString("a")
    String up();

    @DefaultString("b")
    String down();

    @DefaultString("c")
    String left();

    @DefaultString("d")
    String right();

    @DefaultString("hjwifi2014")
    String wifi_ssid();
}
