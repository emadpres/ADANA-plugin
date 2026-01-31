Intent intent = new Intent(this, TheServiceYouWantToStart.class);
PendingIntent pending = PendingIntent.getService(this, 0, intent, 0);
AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
alarm.set(AlarmManager.RTC_WAKEUP, Time_To_wake, pending);;
