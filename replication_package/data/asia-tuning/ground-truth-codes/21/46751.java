(new NotificationCompat.builder(this).setDeleteIntent(deletePendingIntent)).build();
Intent deleteIntent = new Intent(this, YourService.class);
deleteIntent.putExtra(someKey, someIntValue);
PendingIntent deletePendingIntent = PendingIntent.getService(this,
someIntValue, 
deleteIntent, 
PendingIntent.FLAG_CANCEL_CURRENT);;
