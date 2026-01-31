public class MyReceiver extends BroadcastReceiver {
public MyReceiver() {
}

@Override
public void onReceive(Context context, Intent intent) {


    Intent intent1 = new Intent(context, MyNewIntentService.class);
    context.startService(intent1);
}
   };
public class MyNewIntentService extends IntentService {
private static final int NOTIFICATION_ID = 3;


public MyNewIntentService() {
super("MyNewIntentService");
}

@Override
protected void onHandleIntent(Intent intent) {
Notification.Builder builder = new Notification.Builder(this);
    builder.setContentTitle("My Titel");
    builder.setContentText("This is the Body");
    builder.setSmallIcon(R.drawable.whatever);
Intent notifyIntent = new Intent(this, MainActivity.class);
PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//to be able to launch your activity from the notification 
builder.setContentIntent(pendingIntent);
Notification notificationCompat = builder.build();
NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
managerCompat.notify(NOTIFICATION_ID, notificationCompat);
}
 };
Intent notifyIntent = new Intent(this,MyReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast
            (context, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
            1000 * 60 * 60 * 24, pendingIntent);;
