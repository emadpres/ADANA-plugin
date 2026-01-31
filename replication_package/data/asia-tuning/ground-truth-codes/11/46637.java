private void setService() {

                try {
                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                Intent intent = new Intent(this, MyBroadCastReceiver.class);

                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); //
                        // 60 seconds i.e 1 min 
                long time = 60 * 1000;

                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + time, time, pendingIntent);

            };
private void removeService() {
            try {
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        };
public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("test_log", "broadcast worked ");

    }
};
