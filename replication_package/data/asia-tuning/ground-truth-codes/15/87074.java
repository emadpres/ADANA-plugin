public class SimpleKitkatNotificationListener extends NotificationListenerService {

        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
              //..............
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
              //.............. 
        }
};
Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
    startActivity(intent);;
Notification mNotification=sbn.getNotification();
   Bundle extras = mNotification.extras;;
/**
     * {@link #extras} key: this is the title of the notification,
     * as supplied to {@link Builder#setContentTitle(CharSequence)}.*/
    public static final String EXTRA_TITLE = "android.title";

    /**
     * {@link #extras} key: this is the main text payload, as supplied to
     * {@link Builder#setContentText(CharSequence)}.*/
    public static final String EXTRA_TEXT = "android.text";

    /**
     * {@link #extras} key: this is a third line of text, as supplied to
     * {@link Builder#setSubText(CharSequence)}.*/
    public static final String EXTRA_SUB_TEXT = "android.subText";

    /**
     * {@link #extras} key: this is a bitmap to be used instead of the small icon when showing the
     * notification payload, as
     * supplied to {@link Builder#setLargeIcon(android.graphics.Bitmap)}.*/
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";;
String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
     int notificationIcon = extras.getInt(Notification.EXTRA_SMALL_ICON);
     Bitmap notificationLargeIcon = 
                  ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
     CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
     CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);;
