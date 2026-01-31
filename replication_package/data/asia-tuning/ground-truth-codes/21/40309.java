WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 wifi.disconnect();
 discon = new DisconnectWifi();
 registerReceiver(discon, new IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION));;
public class DisconnectWifi extends BroadcastReceiver  {

    @Override
    public void onReceive(Context c, Intent intent) {
        if(!intent.getParcelableExtra(wifi.EXTRA_NEW_STATE).toString().equals(SupplicantState.SCANNING)) wifi.disconnect();
        }
    };
