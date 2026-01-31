Notification notify = createNotification();
final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

notificationManager.notify(ONGOING_NOTIFICATION, notify);;
