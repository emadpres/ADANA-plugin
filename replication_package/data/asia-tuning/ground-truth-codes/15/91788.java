private  void generateNotification(Context context, String message ) {

        int icon = R.drawable.icon;

        long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager)

                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(icon, message);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent;      
        notificationIntent = new Intent(context, ActivityName.class);
        notificationIntent.putExtra("From", "notifyFrag");

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notificationIntent.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);;
String type = getIntent().getStringExtra("From");
if (type != null) {
    switch (type) {
        case "notifyFrag":

        // your activity called from notification 

            break;
    }
};
