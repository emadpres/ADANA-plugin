AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
am.set(AlarmManager.RTC, <trigger time>, <the intent>);;
am.cancel(<the intent>);;
