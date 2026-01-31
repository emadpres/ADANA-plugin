AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10000, 6*60*60*1000, pendingIntent);;
