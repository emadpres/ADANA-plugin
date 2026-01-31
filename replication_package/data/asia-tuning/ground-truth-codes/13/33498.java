//Some Vars
public static final int NOTIFICATION_ID = 1; //this can be any int


//Building the Notification
NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
builder.setSmallIcon(R.drawable.ic_stat_notification);
builder.setContentTitle("BasicNotifications Sample");
builder.setContentText("Time to learn about notifications!");

NotificationManager notificationManager = (NotificationManager) getSystemService(
            NOTIFICATION_SERVICE);
notificationManager.notify(NOTIFICATION_ID, builder.build());;
NotificationCompat.Builder builder = new NotificationCompat.Builder(context);......

NotificationManager notificationManager = (NotificationManager) context.getSystemService(
            context.NOTIFICATION_SERVICE);;
