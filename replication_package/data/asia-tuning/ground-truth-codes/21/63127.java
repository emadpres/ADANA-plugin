AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

// Replace MyActivity.class with the activity class you want to run periodically
Intent i = new Intent(context, MyActivity.class);
PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

long now = System.currentTimeMillis();
long interval = 60 * 60 * 1000;  // one hour

am.setRepeating(AlarmManager.RTC_WAKEUP, now, interval, pi);;
