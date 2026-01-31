public void call() {
    Toast.makeText(this,"call",Toast.LENGTH_SHORT).show();
    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel: 0211234567"));
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle(getResources().getString(R.string.app_name)).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);


    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    notificationBuilder.setStyle(inboxStyle);
    inboxStyle.setBigContentTitle("sdjshfjnds");
    inboxStyle.addLine("sdjjsdfn");
    notificationBuilder.setStyle(inboxStyle);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    int NOTIFICATION_ID = 100;
    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
};
