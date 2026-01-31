// Calculate the time when it expires.long wakeupTime = System.currentTimeMillis() + duration;

    Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent);;
