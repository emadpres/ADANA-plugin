private void sendNotification(String messageBody) {


    Intent intent = new Intent(this, MyNotification.class);
    intent.putExtra("Message",messageBody);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, MyNotification.class), 0);

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Shri Sidhbali Baba").setContentText(messageBody).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationBuilder.setContentIntent(contentIntent);

    notificationManager.notify(0, notificationBuilder.build());
};
