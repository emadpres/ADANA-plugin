Context context = getApplicationContext();
NotificationCompat.Builder builder = new NotificationCompat.Builder(context ).setSmallIcon(R.drawable.ic_launcher);      

Intent intent = new Intent( context, MainActivity.class);
PendingIntent pIntent = PendingIntent.getActivity(context, mID , intent, 0);
builder.setContentIntent(pIntent);
NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

Notification notif = builder.build();
mNotificationManager.notify(mID, notif);;
