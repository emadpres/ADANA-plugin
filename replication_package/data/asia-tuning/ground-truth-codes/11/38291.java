@Override
public void onReceive(Context ctx, Intent intent) {
    if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
        NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        String typeName = info.getTypeName();
        String subtypeName = info.getSubtypeName();
                    boolean available = info.isAvailable();
                    // etc
        }
};
