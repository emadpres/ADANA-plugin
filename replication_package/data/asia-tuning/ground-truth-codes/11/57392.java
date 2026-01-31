public class MyService extends IntentService {

    private AlarmManager alarmManager;

    private boolean started;
    private PendingIntent pendingIntent;

    public MyService() {
        super(MyService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (! started) {
            started = true;

            // Call the service periodically every 15 minutes
            pendingIntent = PendingIntent.getService(
                    getApplicationContext(), 
                    0, 
                    intent, 
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME, 
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    pendingIntent);
        }

        // DO YOUR STUFF HERE
    }
};
