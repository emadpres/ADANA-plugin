Intent intent = new Intent(this, SMSBroadcastReceiver.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 234324243, intent, 0);
AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

alarmManager.cancel(pendingIntent);;
