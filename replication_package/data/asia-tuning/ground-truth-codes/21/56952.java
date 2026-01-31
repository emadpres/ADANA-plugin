PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,  PendingIntent.FLAG_UPDATE_CURRENT);
 notificationManager.cancel(pendingIntent);;
