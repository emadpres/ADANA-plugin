Intent intent = new Intent(getApplicationContext(), NotificationService.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
    Calendar.getInstance().get(Calendar.MILLISECOND), intent,
    PendingIntent.FLAG_UPDATE_CURRENT);;
Intent intent = new Intent(getApplicationContext(), NotificationService.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
    Calendar.getInstance().get(Calendar.MILLISECOND), intent,
    PendingIntent.FLAG_UPDATE_CURRENT);
// Cancel the alarm
AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
alarmManager.cancel(pendingIntent);
// Now cancel the PendingIntent also (just to be sure)
pendingIntent.cancel();;
