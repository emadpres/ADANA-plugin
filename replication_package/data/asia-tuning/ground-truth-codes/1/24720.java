ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnectedOrConnecting();

    if(isConnected)
    {
    if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE)
    return true;    

    else
        return false;
    }

    else
        return false;;
