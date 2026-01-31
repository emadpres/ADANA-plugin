// Add app running notification  

    private void addNotification() {



    NotificationCompat.Builder builder =  
            new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle("Notifications Example").setContentText("This is a test notification");  

    Intent notificationIntent = new Intent(this, MainActivity.class);  
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,   
            PendingIntent.FLAG_UPDATE_CURRENT);  
    builder.setContentIntent(contentIntent);  

    // Add as notification  
    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
    manager.notify(FM_NOTIFICATION_ID, builder.build());  
}  

// Remove notification  
private void removeNotification() {  
    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
    manager.cancel(FM_NOTIFICATION_ID);  
};
