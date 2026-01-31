NotificationManager notificationManager = (NotificationManager) 
                  getSystemService(NOTIFICATION_SERVICE); 

          Intent intent = new Intent(this, EndActivity.class);          
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this).setContentTitle("My message").setContentText("Subject").setSmallIcon(R.drawable.MyApplogo).setContentIntent(pIntent).setAutoCancel(true).setStyle(new Notification.BigTextStyle().bigText(""+msg.get(3))).build();
              //.addAction(R.drawable.line, "", pIntent).build();
        n.flags |= Notification.FLAG_AUTO_CANCEL;
         notificationManager.notify(0, n);;
