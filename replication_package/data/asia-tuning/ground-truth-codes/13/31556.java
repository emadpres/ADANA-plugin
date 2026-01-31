Toast toast = Toast.makeText(context, myMessage, Toast.LENGTH_LONG).show();;
Notification.Builder builder = new Notification.Builder(context);
builder.setSound(Uri.fromFile(yourFile));
builder.setTicker(yourMessage);
NotificationManager.notify(1,builder.getNotification());;
