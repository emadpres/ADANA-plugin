Notification notification = new Notification(R.drawable.direction, "Cool Notification",
                System.currentTimeMillis());
        /********LIKE THIS*********/
        notification.number = notificationCount++;
        /********LIKE THIS*********/

        notification.setLatestEventInfo(context, "Cool Notification Title",
                "updated notificaiton message", null);


        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify(R.id.my_motification, notification);;
