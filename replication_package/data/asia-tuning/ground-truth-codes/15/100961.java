public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this).setContentTitle("test").setContentText(remoteMessage.getData().get("message"));
        NotificationManager manager = (NotificationManager)     getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    enter code here
    };
