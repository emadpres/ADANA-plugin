private void setAlarm(Calendar targetCal){
     Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
     PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
     AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
     alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    };
