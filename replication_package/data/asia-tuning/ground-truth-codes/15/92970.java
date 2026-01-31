NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher)).setContentTitle(title).setContentText(message).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());;
