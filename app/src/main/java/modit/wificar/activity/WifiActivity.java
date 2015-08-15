package modit.wificar.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import modit.wificar.R;

/**
 * Created by legency on 2015/3/31.
 */
@EActivity(R.layout.info)
public class WifiActivity extends Activity {
    @ViewById
    TextView wifiinfo;
    @AfterViews
    void getInfo(){
        WifiManager  wm=(WifiManager)getSystemService(this.WIFI_SERVICE);

        ConnectivityManager connectManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi   = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI  );
        if(wifi!=null&& wifi.getState()   == NetworkInfo.State.CONNECTED){
            wifiinfo.append("wifi已经打开\n");
            WifiInfo info = wm.getConnectionInfo();
            DhcpInfo dhcpinfo = wm.getDhcpInfo();
            wifiinfo.append("SSID:"+info.getSSID()+"\n");
            wifiinfo.append("NetworkID:"+info.getNetworkId()+"\n");
            wifiinfo.append("Gateway:"+intToString(dhcpinfo.gateway)+"\n");
        }
    }
    private String intToString(int a){
        StringBuffer sb=new StringBuffer();
        int b=(a>>0)&0xff;
        sb.append(b+".");
        b=(a>>8)&0xff;
        sb.append(b+".");
        b=(a>>16)&0xff;
        sb.append(b+".");
        b=(a>>24)&0xff;
        sb.append(b);
        return sb.toString();
    }

}
