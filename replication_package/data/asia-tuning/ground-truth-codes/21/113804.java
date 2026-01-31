AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);         
Intent intent = new Intent(getBaseContext(), YourAlarmSetClass.class);      
PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);         
aManager.cancel(pIntent);;
