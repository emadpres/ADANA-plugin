notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);;
public void stopMediaPlayer() {
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.cancelAll();
    mMediaPlayer.release();
}

public void showNotification(){

    builder = new NotificationCompat.Builder(this);

    builder.setTicker("Hello");
    builder.setContentTitle("Helloo");
    builder.setContentText("Helloooo");
    builder.setOngoing(true);

    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    // Will display the notification in the notification bar
    notificationManager.notify(NOTIFICATION_ID, builder.build());

};
