Intent myIntent = new Intent(context,VisitReminderNotificationMessage.class);
PendingIntent pendingIntent = PendingIntent.getService(context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
AlarmManager alarmManager = (AlarmManager) context.getSystemService(VisitReminderNotificationMessage.ALARM_SERVICE);

alarmManager.cancel(pendingIntent);
pendingIntent.cancel();;
