Intent intent = new Intent(this, Mote.class);
     PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1253, intent, 0);
     AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
     alarmManager.cancel(pendingIntent);;
