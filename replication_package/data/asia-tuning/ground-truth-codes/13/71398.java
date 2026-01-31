NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
builder.setContentTitle(getString(R.string.smaple_notification_title));
builder.setSmallIcon(R.drawable.ic_message);
builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, ActivateActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
extender.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.notif_background));
extender.setContentIcon(R.drawable.ic_message);
extender.setHintHideIcon(true);
extender.extend(builder);

builder.setPriority(NotificationCompat.PRIORITY_LOW);
builder.setContentText(notificationText);
builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.large_icon));
notificationManager.notify(messageIndex, builder.build());;
