public static boolean checkInternetConnection(Context context)
{
    try
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected())
                || (mobile != null && mobile.isConnected()))
        {
            return true;
        }
        log("Connection", "Connection failed");
        return false;
    }
    catch (Exception e)
    {
        // TODO: handle exception
        e.printStackTrace();
        return false;
    }
};
