PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, _id,intent, PendingIntent.FLAG_UPDATE_CURRENT);
    alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
            pendingIntent);

public static Context getContext() {
    return mContext;
};
AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intentAlarm = new Intent(AlarmListviewActivity.this,
                        MainActivity.class);
                PendingIntent morningIntent = PendingIntent.getBroadcast(MainActivity.getContext(), Alarm_id.get(positon),
                        intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);

                alarmManager.cancel(morningIntent);
                morningIntent.cancel();;
