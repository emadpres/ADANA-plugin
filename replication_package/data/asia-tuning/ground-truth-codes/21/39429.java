AlarmManager alarmMgr = alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
Intent alarmIntent = alarmIntent = new Intent("AlarmIntentReceiver"); 
PendingIntent pendingAlarmIntent = pendingAlarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, alarmIntent, 0);
alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 30*1000, 3*60*1000,  pendingAlarmIntent); //start in 30 secs and rest in 3 mins interval;
alarmMgr.cancel(pendingAlarmIntent);;
