switch (am.getRingerMode()) {

    case AudioManager.RINGER_MODE_VIBRATE:
        Log.i("MyApp","Vibrate mode");
        displayNotification("Hi i am notification");
        break;

};
public void displayNotification(String msg)
{
NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
Notification notification = new Notification(R.drawable.icon, msg, System.currentTimeMillis());

// The PendingIntent will launch activity if the user selects this notification
PendingIntent contentIntent = PendingIntent.getActivity(this, REQUEST_CODE, new Intent(this, ExpandNotification.class), 0);

notification.setLatestEventInfo(this, "Title here", ".. And here's some more details..", contentIntent);

manager.notify(NOTIFICATION_ID, notification);

};
