alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
 Date date= new Date();
 long timer = date.getTime()+inactivityTimer;
 Intent intentAlarm = new Intent(ACTION );
 alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    timer,
                    PendingIntent.getBroadcast(context ,1,  intentAlarm , PendingIntent.FLAG_UPDATE_CURRENT ));;
