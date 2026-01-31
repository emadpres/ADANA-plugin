mMN = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);        

Intent nid = new Intent(MainActivity.this, stopservice.class);
// If you were starting a service, you wouldn't using getActivity() here
PendingIntent ci = PendingIntent.getActivity(MainActivity.this, NOTIFICATION_ID, nid, PendingIntent.FLAG_UPDATE_CURRENT);

Notification.Builder builder = new Notification.Builder(MainActivity.this);
builder.setContentTitle("TAP").setContentText("Tap here to exit").setTicker("Tap this notification to exit").setSmallIcon(R.drawable.ic_launcher).setContentIntent(ci).setAutoCancel(true); // auto cancel means the notification will remove itself when pressed

mMN.notify(NOTIFICATION_ID, builder.getNotification());;
