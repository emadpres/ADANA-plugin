Intent notificationIntent = new Intent(ctx, YourClass.class);
PendingIntent contentIntent = PendingIntent.getActivity(ctx,
        YOUR_PI_REQ_CODE, notificationIntent,
        PendingIntent.FLAG_CANCEL_CURRENT);

NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

Resources res = ctx.getResources();
Notification.Builder builder = new Notification.Builder(ctx);

builder.setContentIntent(contentIntent).setSmallIcon(R.drawable.some_img).setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.some_big_img)).setTicker(res.getString(R.string.your_ticker)).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle(res.getString(R.string.your_notif_title)).setContentText(res.getString(R.string.your_notif_text));
Notification n = builder.build();

nm.notify(YOUR_NOTIF_ID, n);;
