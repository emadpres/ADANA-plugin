AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    Intent intent=new Intent(this,AlarmManagerBroadcast.class);
    PendingIntent pi=PendingIntent.getBroadcast(this,alarmid,intent,0);
    alarmManager.cancel(pi);;
