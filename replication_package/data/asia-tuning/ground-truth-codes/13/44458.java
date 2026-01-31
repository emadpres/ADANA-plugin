int notificationId = new Random().nextInt(); // just use a counter in some util class...
PendingIntent dismissIntent = NotificationActivity.getDismissIntent(notificationId, context);

NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
builder.setPriority(NotificationCompat.PRIORITY_MAX) //HIGH, MAX, FULL_SCREEN and setDefaults(Notification.DEFAULT_ALL) will make it a Heads Up Display Style.setDefaults(Notification.DEFAULT_ALL) // also requires VIBRATE permission.setSmallIcon(R.drawable.ic_action_refresh) // Required!.setContentTitle("Message from test").setContentText("message").setAutoCancel(true).addAction(R.drawable.ic_action_cancel, "Dismiss", dismissIntent).addAction(R.drawable.ic_action_boom, "Action!", someOtherPendingIntent);

// Gets an instance of the NotificationManager service
NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// Builds the notification and issues it.notifyMgr.notify(notificationId, builder.build());;
public class NotificationActivity extends Activity {

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish(); // since finish() is called in onCreate(), onDestroy() will be called immediately
    }

    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }

};
