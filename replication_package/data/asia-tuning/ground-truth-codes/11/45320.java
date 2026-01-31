AlarmManager am;...
am.cancel ( PendingIntent operation );;
Intent intent = new Intent(this, AlarmReceive.class);
PendingIntent sender = PendingIntent.getBroadcast(this,0, intent, 0);
AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

alarmManager.cancel(sender);;
