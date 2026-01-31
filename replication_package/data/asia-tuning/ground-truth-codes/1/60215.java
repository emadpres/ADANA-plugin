ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
NetworkInfo wifi = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

if (wifi.isConnected()) {
    // Do your code
};
android.permission.ACCESS_NETWORK_STATE;
