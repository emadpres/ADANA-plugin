NotificationManager notificationManager =
    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
Notification notification = new Notification(/* your notification */);
PendingIntent pendingIntent = /* your intent */;
notification.setLatestEventInfo(this, /* your content */, pendingIntent);
notificationManager.notify(/* id */, notification);;
