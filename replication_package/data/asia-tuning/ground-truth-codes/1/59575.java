//Function For Check WIFI Connection
    public boolean fun_CheckWIFIConnection(String WIFIName) {

        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (networkInfo.isConnected()) {

                final WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();

                if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {

                    String SSID = connectionInfo.getSSID();

                    if (SSID.startsWith("\""))
                    {
                        SSID = SSID.substring(1, SSID.length());
                    }
                    if (SSID.endsWith("\""))
                    {
                        SSID = SSID.substring(0, (SSID.length() -1));
                    }
                    if (SSID.equals(WIFIName))
                    {
                        return true;
                    }

                }
                else{
                    Log.e("Request Alert", "connectionInfo Is null OR SSID Is Blanck");
                    return false;
                }
            }
            else{
                    Log.e("Request Alert", "WIFI Is Not Connected");
                    return false;

            }

        return false;
    };
