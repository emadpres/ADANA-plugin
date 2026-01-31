IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
registerReceiver(broadcastReceiver, intentFilter);;
@Override
public void onReceive(Context context, Intent intent) {
    final String action = intent.getAction();
    if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
        if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
            //wifi is connected.You can do stuff here.} else {
            // wifi connection is gone.}
    };
WifiManager wifiManager = Context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    if (wifiInfo != null) {
        //connection is established
    };
