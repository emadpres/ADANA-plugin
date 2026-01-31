Intent intent = new Intent(this, RepeatingAlarmService.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0);
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + interval,
            interval,
            pendingIntent);;
