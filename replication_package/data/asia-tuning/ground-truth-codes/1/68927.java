private Boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager 
          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
}

public Boolean isWifiConnected(){
    if(isNetworkAvailable()){
        ConnectivityManager cm 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
    }
    return false;
}

public Boolean isEthernetConnected(){
    if(isNetworkAvailable()){
        ConnectivityManager cm 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_ETHERNET);
    }
    return false;
};
