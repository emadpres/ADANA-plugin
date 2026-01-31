Intent intent = new Intent(
    getApplicationContext(),
    PushMainActivity.class);
PendingIntent pendingIntent = PendingIntent.getActivity(
    ListAddActivity.this,
    ALARM_CODE,
    intent,
    PendingIntent.FLAG_UPDATE_CURRENT);
alarmManager.cancel(pendingIntent);;
