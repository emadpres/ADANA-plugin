private static final int NOTIFICATION_EX = 1;
private NotificationManager notificationManager;

@Override
public void onCreate() {
    super.onCreate();

    notificationManager = (NotificationManager) 
        getSystemService(Context.NOTIFICATION_SERVICE);

    int icon = R.drawable.notification_icon;
    CharSequence tickerText = "Hello";
    long when = System.currentTimeMillis();

    Notification notification = new Notification(icon, tickerText, when);

    Context context = getApplicationContext();
    CharSequence contentTitle = "My notification";
    CharSequence contentText = "Hello World!";
    Intent notificationIntent = new Intent(this, MyClass.class);
    PendingIntent contentIntent = PendingIntent.getActivity(this, 
        0, notificationIntent, 0);

    notification.setLatestEventInfo(context, contentTitle, 
        contentText, contentIntent);

    notificationManager.notify(NOTIFICATION_EX, notification);
}

@Override
protected void onPause() {
    super.onPause();
    if (isFinishing()) {
        notificationManager.cancel(NOTIFICATION_EX);
    }
};
