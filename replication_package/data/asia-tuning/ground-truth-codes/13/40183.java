private void testNotification() {
    Notification.Builder builder = new Notification.Builder(this);
    builder.setSmallIcon(R.drawable.ic_launcher).setPriority(Notification.PRIORITY_HIGH).setOngoing(true);
    builder.setLights(0xff00ff00, 300, 100);
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.notify(1, builder.build());
};
