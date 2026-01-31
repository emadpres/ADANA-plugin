Intent intent = new Intent(this, typeof(SomeActivityInYourApp));
PendingIntent pi = PendingIntent.getActivity(this, 0, intent,   PendingIntent.FLAG_UPDATE_CURRENT);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

builder.setSmallIcon(Resource.Drawable.my_icon);
builder.setTicker("App info string");
builder.setContentIntent(pi);
builder.setOngoing(true);
builder.setOnlyAlertOnce(true);

Notification notification = builder.build();

// optionally set a custom view

startForeground(SERVICE_NOTIFICATION_ID, notification);;
