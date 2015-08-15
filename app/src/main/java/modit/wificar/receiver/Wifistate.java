package modit.wificar.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.androidannotations.annotations.EReceiver;

/**
 * Created by legency on 2015/4/3.
 */
@EReceiver
public class Wifistate extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // empty, will be overridden in generated subclass
    }
}