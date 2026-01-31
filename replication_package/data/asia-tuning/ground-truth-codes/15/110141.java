NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title");
Intent resultIntent = new Intent(this, MyActivity.class);
PendingIntent resultPendingIntent = PendingIntent.getActivity(
this,
0,
resultIntent,
PendingIntent.FLAG_UPDATE_CURRENT);
mBuilder.setContentIntent(resultPendingIntent);
Notification notification = mBuilder.build();
notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
mNotifyMgr.notify(NOTIFICATION_ID, notification);;
mNotifyMgr.cancel(NOTIFICATION_ID);;
