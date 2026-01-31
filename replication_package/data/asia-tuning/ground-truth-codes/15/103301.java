try {
    NotificationManager mNotificationManager = null;
    Class<?> c = Class.forName("android.app.NotificationManager");
    Method method = c.getMethod("getService");
    Object obj = method.invoke(mNotificationManager);
    Class<?> clazz = Class.forName("android.app.INotificationManager$Stub$Proxy");
    Method areNotificationsEnabledForPackage = clazz.getMethod("areNotificationsEnabledForPackage", String.class, int.class);
    boolean blocked = (boolean) areNotificationsEnabledForPackage.invoke(obj, getPackageName(), android.os.Process.myUid());
    Log.d(MainActivity.class.getSimpleName(), String.valueOf(blocked));
} catch (Exception e) {
    e.printStackTrace();
};
boolean blocked = (boolean) areNotificationsEnabledForPackage.invoke(obj, getPackageName(), android.os.Process.myUid());;
//InvocationTargetException will be thrown.boolean blocked = (boolean) areNotificationsEnabledForPackage.invoke(obj, "com.android.camera", 10040);;
