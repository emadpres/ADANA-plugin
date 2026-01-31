WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
if (wifiManager != null)
  wifiManager.setWifiEnabled(true);;
