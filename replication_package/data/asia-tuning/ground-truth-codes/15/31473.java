WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
   Log.d("wifiInfo", wifiInfo.toString());
   Log.d("SSID",wifiInfo.getSSID());;
