public static ShowAvailable() 
 {
   ConnectivityManager connectivityMgr = (ConnectivityManager)
   getSystemService(Context.CONNECTIVITY_SERVICE);
   NetworkInfo[] nwInfos = connectivityMgr.getAllNetworkInfo(); 
       for (NetworkInfo nwInfo : nwInfos)  
          {  
             Log.d(TAG, "Network Type Name: " +
             nwInfo.getTypeName());   Log.d(TAG, "Network available: " +
             nwInfo.isAvailable());   Log.d(TAG, "Network c_or-c: " +
             nwInfo.isConnectedOrConnecting());   Log.d(TAG, "Network connected: "
             + nwInfo.isConnected()); 
          }

};
