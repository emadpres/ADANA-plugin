public class WifiReceiver extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent intent) {

  NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
  if(info != null) {
     if(info.isConnected()) {
        // Do your work.// e.g.To check the Network Name or other info:
   WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
     }
  }
 }
 };
