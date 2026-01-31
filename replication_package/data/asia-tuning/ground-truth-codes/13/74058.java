NotificationCompat.Builder builder = new NotificationCompat.Builder(
            context);

    /** IMPORTANT : Create an intent which starts the activity **/
    Intent intent = new Intent(context, ActivityName.class);

    /** IMPORTANT : Ensure that you use getActivity with the PendingIntent.**/
    /**This pending intent will be fired when you click on the notification.**/ 
    builder.setContentIntent(PendingIntent.getActivity(context, 0, intent,
            0));
    builder.setContentTitle(title);
    builder.setContentText(message);
    builder.setSmallIcon(R.drawable.notification_icon);
    Notification notification = builder.build();
    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    manager.notify(0, notification);;
