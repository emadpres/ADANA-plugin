static ArrayList<String>notifIds=new ArrayList<>();

//your code


notificationManager.notify("myappnotif",NOTIFICATION_COUNT, builder.build());
        notifIds.add(data);;
NotificationManager notificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);
    for(int i=0;i<MainActivity.notifIds.size();i++){
     notificationManager.cancel("myappnotif", i);
     };
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

     NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
     notificationManager.cancelAll();
  };
