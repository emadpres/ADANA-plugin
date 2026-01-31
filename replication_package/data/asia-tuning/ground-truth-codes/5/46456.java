String networkSSID = "test";
String networkPass = "pass";

WifiConfiguration conf = new WifiConfiguration();
conf.SSID = "\"" + networkSSID + "\"";   //;
conf.wepKeys[0] = "\"" + networkPass + "\""; 
conf.wepTxKeyIndex = 0;
conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);;
conf.preSharedKey = "\""+ networkPass +"\"";;
conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);;
WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
wifiManager.add(conf);;
List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
for (WifiConfiguration i : list) {
    if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
        wm.disconnect();
        wm.enableNetwork(i.networkId, true);
        wm.reconnect();
        break;
    }
};
