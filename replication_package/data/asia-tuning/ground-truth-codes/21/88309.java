@Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "---------------------------------------");
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            // Do Some Thing here...
        }
};
