public class InternetData {
    Activity activity;
    ConnectivityManager connManager;
    NetworkInfo mWifi;

    public InternetData(Activity activity){
        this.activity = activity;
        connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);      
    }

    public boolean hasInternetConnection(){
        if(mWifi.isConnected())
            return true;

        return false;
    }
};
