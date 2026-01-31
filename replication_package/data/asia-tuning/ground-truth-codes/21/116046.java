private void cancelAlarm(int RSQ) {

            textAlarmPrompt.setText(
                    "\n\n***\n"
                            + "Alarm Cancelled! \n"
                            + "***\n");

            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RSQ, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        };
cancelAlarm(1);
cancelAlarm(2);
cancelAlarm(3);
cancelAlarm(4);;
for(int i=1;i<5;i++)
{
  cancelAlarm(i);
};
