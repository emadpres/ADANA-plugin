private void checkNetworkConnection() {
      ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
      if (activeInfo != null && activeInfo.isConnected()) {
          wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
          mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
          if(wifiConnected) {
              Log.i(TAG, getString(R.string.wifi_connection));
          } else if (mobileConnected){
              Log.i(TAG, getString(R.string.mobile_connection));
          }
      } else {
          Log.i(TAG, getString(R.string.no_wifi_or_mobile));
      }
}