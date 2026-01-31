Notification notification = new Notification(
    android.R.drawable.stat_sys_warning,  //Icon to use
    "Hello World!", //Text
    System.currentTimeMillis() //When to display - i.e.now
);;
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setData(Uri.parse("http://www.stackoverflow.com"));
PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

//Add to the Notification
notification.setLatestEventInfo(
    getApplicationContext(),
    "Stack Overflow", //Title of detail view
    "This will launch Stack Overflow",  //Text on detail view
    pi
);
//Display the Notification
NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
nm.notify(ID_HELLO_WORLD, notification);  //ID_HELLO_WORLD is a int ID;
