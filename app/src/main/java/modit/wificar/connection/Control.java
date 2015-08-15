package modit.wificar.connection;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import modit.wificar.config.CarConfig_;

/**
 * Created by legency on 2015/3/31.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Control {

    @RootContext
    Context context;

    static PrintWriter mPrintWriterClient = null;
    static BufferedReader mBufferedReaderClient	= null;
    private Socket mSocketClient = null;
    public boolean connected = false;
    private String serverIp="192.168.1.1";
//    private String serverIp="192.168.1.1";
    private int serverPort = 2001;

    @Pref
    CarConfig_ config_;

    public void initUrl(){
        serverIp= config_.ip().get();
        serverPort= Integer.valueOf(config_.port_control().get());
    }



    public void send(String commend){
        Log.i("commend",commend);
        if(connected){
            Log.i("connection","send");
            mPrintWriterClient.print(commend);
            mPrintWriterClient.flush();
        }
    }

    private String getInfoBuff(char[] buff, int count)
    {
        char[] temp = new char[count];
        for(int i=0; i<count; i++)
        {
            temp[i] = buff[i];
        }
        return new String(temp);
    }

    public void turnon_wifi(){
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    public void wificonnect(){
        String networkSSID = "hjwifi2014";
        String networkPass = "pass";

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";

        conf.wepKeys[0] = "\"" + networkPass + "\"";
        conf.wepTxKeyIndex = 0;
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }


    @Background
    public void connect(){
        Log.i("connection",serverIp+"connected"+serverPort);
        if(connected)return;
        try
        {
            //连接服务器
            mSocketClient = new Socket(serverIp, serverPort);	//portnum
            //取得输入、输出流
            mBufferedReaderClient = new BufferedReader(new InputStreamReader(mSocketClient.getInputStream()));
            mPrintWriterClient = new PrintWriter(mSocketClient.getOutputStream(), true);
            connected = true;
            Log.i("connection",serverIp+"connected"+serverPort);
        }
        catch (Exception e)
        {
            Log.e("connection", serverIp + "connected" + serverPort);
        }

        char[] buffer = new char[256];
        int count = 0;
        while (true)
        {
            try
            {
                //if ( (recvMessageClient = mBufferedReaderClient.readLine()) != null )
                if((count = mBufferedReaderClient.read(buffer))>0)
                {
                }
            }
            catch (Exception e)
            {
                Log.e("connection", serverIp + "connected" + serverPort);
            }
        }
    }
}
