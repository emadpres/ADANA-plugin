NotificationCompat.Builder builder = new NotificationCompat.Builder(
    this).setSmallIcon(R.drawable.ic_myicon).setContentTitle(title).setAutoCancel(true);
Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/mymp3");
builder.setSound(alarmSound);...
builder.setContentIntent(pendingIntent);
NotificationManager manager = (NotificationManager)
    getSystemService(Context.NOTIFICATION_SERVICE);
manager.notify(1, builder.build());;
Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/mymp3");;
