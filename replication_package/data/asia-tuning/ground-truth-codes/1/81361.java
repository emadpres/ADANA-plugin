public boolean isWifiAvailable(Context context){

    boolean wifiConnected = false;

    ConnectivityManager connMgr =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

    if (activeInfo != null && activeInfo.isConnected()) {
        wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;            
    } else {
        wifiConnected = false;
    }

    return wifiConnected;
};
isWifiAvailable(this);
