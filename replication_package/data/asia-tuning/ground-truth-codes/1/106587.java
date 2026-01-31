final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
if (activeNetwork != null && activeNetwork.isConnected()) {
    // notify user you are online
} else {
    // notify user you are not online
};
