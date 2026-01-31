// This is the icon to use on the notification
    int icon = R.drawable.ic_dialog_alert;
    // This is the scrolling text of the notification
    CharSequence text = "Your notification time is upon us".// What time to show on the notification
    long time = System.currentTimeMillis();

    Notification notification = new Notification(icon, text, time);;
