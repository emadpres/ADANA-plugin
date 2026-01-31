Intent intent  = new Intent(getBaseContext(), AlarmReceiver.class);
 PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), identificador , intent, PendingIntent.FLAG_NO_CREATE);
 AlarmManager alarmManager = (AlarmManager)getSystemService(getApplicationContext().ALARM_SERVICE);
 alarmManager.cancel(pendingIntent);;
