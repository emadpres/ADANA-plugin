this.registerReceiver(this.receiver, 
        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));;
private BroadcastReceiver receiver = new BroadcastReceiver(){
    @Override
    public void onReceive(Context arg0, Intent intent) {

    int level = intent.getIntExtra("level", 0);
    Log.e("test", String.valueOf(level) + "%");

     }
};;
this.unregisterReceiver(this.receiver);;
