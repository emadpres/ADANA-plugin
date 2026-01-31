NotificationManager nMgr = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
nMgr.cancel(notifyId); <-----this integer needs to be the same as the one shown.;
private void dontShowNotification(){
    builder.setOngoing(false);
    builder.setAutoCancel(false);
    builder.setContentTitle("Take Screenshot");
    builder.setContentText("");
    builder.setSmallIcon(R.drawable.ic_notification);
    Notification notification = builder.build();
    NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    manager.notify(1, notification);
};
private void showNotification(boolean b) {
    builder.setAutoCancel(false);
    builder.setContentTitle("Take Screenshot");
    builder.setContentText("");
    builder.setSmallIcon(R.drawable.ic_notification);
    builder.setOngoing(b);

    Notification notification = builder.build();
    NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    manager.notify(1, notification);
}

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    CheckBox cb1 = (CheckBox) findViewById(R.id.cb_1);

    cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            showNotification(isChecked);
        }
    }
});
};
