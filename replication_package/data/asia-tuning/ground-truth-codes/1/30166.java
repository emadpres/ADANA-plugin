String networkStatus = "disconnected";
            int netType = 0;
            try{
            ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager != null ){
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if(networkInfo != null){
                        netType = networkInfo.getType();
                        Log.d("Log", "connetion is available");
                    }else {
                        Log.d("Log", "connetion is  not available");
                        return networkStatus;
                    }

                //  if(networkInfo.isAvailable()){  // Old one
if(networkInfo.isAvailable() && networkInfo.isConnected()){  // New change added here
                        if(netType == ConnectivityManager.TYPE_WIFI)
                            {}
                        else if(netType == ConnectivityManager.TYPE_MOBILE )
                            {}
                            }
                        }
                    }catch(Exception e){
            Log.d("Log", "checkNetworkConnection" + e.toString());
            return networkStatus;
        };
