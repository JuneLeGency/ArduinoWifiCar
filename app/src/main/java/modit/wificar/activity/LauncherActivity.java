package modit.wificar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import modit.wificar.R;

/**
 * Created by legency on 2015/4/3.
 */
@EActivity(R.layout.launcher)
public class LauncherActivity extends Activity{

    @Click
    void version1Clicked(){
        Intent i=new Intent(this,VideoActivity_.class);
        startActivity(i);
    }
    @Click
    void version2Clicked(){
        Intent i=new Intent(this,WebViewActivity_.class);
        startActivity(i);
    }
    @Click
    void settingClicked(){
        Intent i=new Intent(this,SettingActivity_.class);
        startActivity(i);
    }

    @Click
    void auto_connectClicked(){
        connectWifi();
    }

    void connectWifi(){
        String networkSSID = "Xiaomi";
        String networkPass = "ychouchou.com";

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";

        conf.wepKeys[0] = "\"" + networkPass + "\"";
        conf.wepTxKeyIndex = 0;
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            Log.i("wifi", i.SSID);
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                Log.i("wifi", "found");
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }
}
