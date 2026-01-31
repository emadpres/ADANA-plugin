Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
r = RingtoneManager.getRingtone(getApplicationContext(), notification);
r.play();;
private static final int MY_NOTIFICATION_ID = 1;
private NotificationManager notificationManager;
private Notification myNotification;
private final String myBlog = "abc"; 

buttonSend.setOnClickListener(new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            notificationManager = (NotificationManager)   getSystemService(Context.NOTIFICATION_SERVICE);
            myNotification = new Notification(R.drawable.ic_launcher,
                    "Notification!", System.currentTimeMillis());
            Context context = getApplicationContext();
            String notificationTitle = "Exercise of Notification!";
            String notificationText = "hello";
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    MainActivity.this, 0, myIntent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            myNotification.defaults |= Notification.DEFAULT_SOUND;
            myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
            myNotification.setLatestEventInfo(context, notificationTitle,
                    notificationText, pendingIntent);
            notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

    }
    });;
