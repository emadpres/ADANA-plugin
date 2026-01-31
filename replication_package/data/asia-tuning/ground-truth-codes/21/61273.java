public class AlarmService extends Service {

    Time time;
    AlarmManager alarmMan;

    @Override
    public void onCreate() {
        alarmMan = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        time = new Time();
    }

    @Override
    public int onStartCommand(Intent intent, int startID, int flags) {
        time.setToNow();
        alarmMan.set(AlarmManager.RTC_WAKEUP, time.toMillis(false)+(10*1000), getPIntent());
        time = null;
    }

   public PendingIntent getPIntent() {
        Intent startIntent = new Intent(this, NotifyService.class);
        startIntent.setAction(com.berrmal.remindme.NotifyService.ACTION_SEND_NOTIFICATION);
        PendingIntent pIntent = PendingIntent.getService(this, 0, startIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pIntent;
    };
