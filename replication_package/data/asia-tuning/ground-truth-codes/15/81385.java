public void onReceive(Context context, Intent intent) {
          // start application here
    NotificationManager notificationManager = (NotificationManager)
                         context.getSystemService(Context.NOTIFICATION_SERVICE);

    Notification notification = intent.getParcelableExtra(NOTIFICATION);
    int id = intent.getIntExtra(NOTIFICATION_ID, 0);

    Intent notificationIntent = new Intent(context, HomeActivity.class);
notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    PendingIntent intentn = PendingIntent.getActivity(context, 0,
            notificationIntent, 0);
    notification.setLatestEventInfo(context, title, message, intentn);
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    notificationManager.notify(id, notification); 
  };
