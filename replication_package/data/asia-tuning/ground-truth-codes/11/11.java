Intent intent = new Intent(getActivity(), MainActivity.class);
intent.setAction(Intent.ACTION_MAIN);
intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), REQUEST_CODE, intent, 0);

int alarmType = AlarmManager.ELAPSED_REALTIME;
final int FIFTEEN_SEC_MILLIS = 15000;
AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
        FIFTEEN_SEC_MILLIS, pendingIntent);
Log.i("RepeatingAlarmFragment", "Alarm set.");