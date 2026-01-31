ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo Wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
if (Wifi.isConnected()) { return true; };
WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
WifiInfo wifiInfo = wifiManager.getConnectionInfo();
return wifiInfo.getSSID();
