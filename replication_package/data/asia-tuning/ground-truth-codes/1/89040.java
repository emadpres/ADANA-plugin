ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
                          activeNetwork.isConnectedOrConnecting();;
boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;;
ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo info = cm.getActiveNetworkInfo();

if (info != null) {
    return info.isConnectedOrConnecting();
};
