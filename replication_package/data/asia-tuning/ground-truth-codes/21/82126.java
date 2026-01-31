AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
Intent myIntent = new Intent(getApplicationContext(),
        SessionReceiver.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, myIntent, 0);

alarmManager.cancel(pendingIntent);;
Intent myIntent = new Intent(getApplicationContext(),
                  SessionReceiver.class);
 PendingIntent pendingIntent = PendingIntent.getBroadcast(
              getApplicationContext(), 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

 AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC, now.getTimeInMillis(),
                    pendingIntent);;
AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
  Intent myIntent = new Intent(getApplicationContext(),
                SessionReceiver.class);
  PendingIntent pendingIntent = PendingIntent.getBroadcast(
          getApplicationContext(), 1, myIntent,     PendingIntent.FLAG_UPDATE_CURRENT);

 alarmManager.cancel(pendingIntent);;
