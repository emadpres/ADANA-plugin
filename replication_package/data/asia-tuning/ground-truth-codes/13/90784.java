NotificationCompat.Builder:contains the UI specification and action information
NotificationCompat.Builder.build() :used to create notification (Which returns Notification object)
Notification.InboxStyle: used to group the notifications belongs to same ID
NotificationManager.notify():to issue the notification.;
private final int NOTIFICATION_ID = 237;
private static int value = 0;
Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.push_notify_icon);
public void buttonClicked(View v)
{
        value ++;
        if(v.getId() == R.id.btnCreateNotify){
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this);            
            builder.setContentTitle("Lanes");
            builder.setContentText("Notification from Lanes"+value);
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(bitmap);
            builder.setAutoCancel(true);
            inboxStyle.setBigContentTitle("Enter Content Text");
            inboxStyle.addLine("hi events "+value);
            builder.setStyle(inboxStyle);
            nManager.notify("App Name",NOTIFICATION_ID,builder.build());
        }
};
