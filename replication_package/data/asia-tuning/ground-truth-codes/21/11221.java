package io.appalert.appalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by alexabraham on 10/4/14.*/
public class AppCheckBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "AppCheckBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Starting Service");
        Intent startServiceIntent = new Intent(context, AppCheckService.class);
        context.startService(startServiceIntent);
    }

}
