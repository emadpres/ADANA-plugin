final NotificationManager notificationManager = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);

final Notification notification = new Notification(R.drawable.icon,"A New Message!",System.currentTimeMillis());

notification.defaults=Notification.FLAG_ONLY_ALERT_ONCE+Notification.FLAG_AUTO_CANCEL;
Intent notificationIntent = new Intent(this, AndroidNotifications.class);
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

notification.setLatestEventInfo(AndroidNotifications.this, title,message, pendingIntent);
notificationManager.notify(NOTIFICATION_ID, notification);;
notificationManager.cancel(NOTIFICATION_ID);;
notificationManager.cancelAll();;
