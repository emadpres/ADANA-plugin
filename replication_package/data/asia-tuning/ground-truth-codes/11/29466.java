PendingIntent sender = PendingIntent.getBroadcast(MyAlarm.this,0,intent, 0);
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), sender);;
alarmManager.cancel(sender);;
