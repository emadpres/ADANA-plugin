private void setRecurringAlarm(Context context) {
    Intent downloader = new Intent(this, MyStartServiceReceiver.class);
    downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, downloader, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(pendingIntent);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6000, 10000, pendingIntent);
};
public class MyStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    //do the stuff...

    }
};
