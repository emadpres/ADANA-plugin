public void sendNotification(View view) {

    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setSmallIcon(R.drawable.ic_stat_notification);
    builder.setContentIntent(pendingIntent);
    builder.setAutoCancel(true);
    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
    builder.setContentTitle("BasicNotifications Sample");
    builder.setContentText("Time to learn about notifications!");
    builder.setSubText("Tap to view documentation about notifications.");

    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, builder.build());
}