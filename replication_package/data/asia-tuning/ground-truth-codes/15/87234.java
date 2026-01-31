Intent i = new Intent(this, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, i,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentTitle("I want food").setContentText(notificationcontent).setSmallIcon(R.drawable.ic_launcher).setContentIntent(pi).setAutoCancel(true).setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                MediaPlayer mp= MediaPlayer.create(contexto, R.raw.your_sound);
                mp.start();
                manager.notify(73195, builder.build());;
