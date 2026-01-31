String ns = Context.NOTIFICATION_SERVICE;
NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
nMgr.cancel(notify_id);;
