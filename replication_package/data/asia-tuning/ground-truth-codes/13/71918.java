NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    Notification notification = new Notification(R.drawable.ic_launcher,
            "Hello from service", System.currentTimeMillis());
    Intent intent = new Intent(this, MainActivity.class);
    notification.setLatestEventInfo(this, "contentTitle", "contentText",
    PendingIntent.getActivity(this, 1, intent, 0));
    manager.notify(123, notification);;
