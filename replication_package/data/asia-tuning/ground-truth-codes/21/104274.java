public class MY_SERVICE extends Service {

@Override
public int onStartCommand(Intent intent, int flags, int startId) {

     // remove last alarm you set
     Intent intent = new Intent(context, MY_DB_PROCEDURE_BROADCAST.class);
    intent.setAction("myDbProcedure");
    PendingIntent alarmIntent = PendingIntent.getBroadcast(context,1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmMgr.cancel(alarmIntent);

    // add new alarm manager
    intent = new Intent(context, MY_DB_PROCEDURE_BROADCAST.class);
    intent.setAction("myDbProcedure");
    alarmIntent = PendingIntent.getBroadcast(ct, 1, intent, 0);
    alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),
            intervalMin*60*1000, alarmIntent);

    return START_STICKY;
}

@Nullable
@Override
public IBinder onBind(Intent intent) {
    return null;
};
public class MY_DB_PROCEDURE_BROADCAST extends BroadcastReceiver {

    private final String TAG=getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Started");
        // DO YOUR DB TASK HERE
    };
