private WifiManager wifiManager;
@Override
public void onCreate(Bundle icicle) {....................
     wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
    if(wifiManager.isWifiEnabled()){
         // do whatever you wnat
         wifiManager.setWifiEnabled(false);
    }else{
       // do whatever you wnat
       wifiManager.setWifiEnabled(true);
    }
};
