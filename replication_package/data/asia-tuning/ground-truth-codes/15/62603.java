NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
notificationManager.cancel(NOTIFICATION_ID);;
notificationManager.cancelAll();;
