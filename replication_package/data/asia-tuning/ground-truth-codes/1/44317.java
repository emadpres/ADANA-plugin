final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
final int apState = (Integer) wifiManager.getClass().getMethod("getWifiApState").invoke(wifiManager);

if (apState == 13) {
    // Ap Enabled
};
public class WifiAPReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "android.net.wifi.WIFI_AP_STATE_CHANGED") {
            int apState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            if (apState == 13) {
                // Hotspot AP is enabled
            } else {
                // Hotspot AP is disabled/not ready
            }
        }
    }
};
