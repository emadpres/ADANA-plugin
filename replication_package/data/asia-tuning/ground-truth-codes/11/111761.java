Intent intent = new Intent("com.my.package.MY_UNIQUE_ACTION");
PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, 
                                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
Calendar calendar = Calendar.getInstance();
calendar.setTimeInMillis(System.currentTimeMillis());
calendar.add(Calendar.MINUTE, 1);

AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);;
boolean alarmUp = (PendingIntent.getBroadcast(context, 0, 
        new Intent("com.my.package.MY_UNIQUE_ACTION"), 
        PendingIntent.FLAG_NO_CREATE) != null);

if (alarmUp)
{
    Log.d("myTag", "Alarm is already active");
};
