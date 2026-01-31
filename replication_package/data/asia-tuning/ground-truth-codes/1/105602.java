private boolean checkInternetConnection() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    // test for connection
    if (cm.getActiveNetworkInfo() != null
            && cm.getActiveNetworkInfo().isAvailable()
            && cm.getActiveNetworkInfo().isConnected()) {
        return true;
    } else {
        Log.v(TAG, "Internet Connection Not Present");
        return false;
    }
};
