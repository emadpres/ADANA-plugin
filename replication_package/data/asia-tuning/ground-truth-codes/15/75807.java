private int notificationID = 100;

  private void giveNotification(String notificationTitle, String bodytext)
  {
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    @SuppressWarnings("deprecation")
    Notification notification = new Notification(R.drawable.ic_launcher, "New Message", System.currentTimeMillis());

    Intent notificationIntent = new Intent(this, DeliveryReport.class);

    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationID, notificationIntent, 0);

    notification.setLatestEventInfo(getApplicationContext(), notificationTitle, bodytext, pendingIntent);
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    notificationManager.notify(notificationID++, notification);
  };
