public void WifiOn(Context context) {
        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mainWifiObj.setWifiEnabled(true);

    }

    public void WifiOff(Context context) {
        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mainWifiObj.setWifiEnabled(false);

    };
