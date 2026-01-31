NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        //other flags.setAutoCancel(true);;
NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
mNotificationManager.cancel(NOTIFICATION_ID);;
