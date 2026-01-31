Intent intentLogo = new Intent(context, MainActivity.class);
Bundle extrasLogo = new Bundle();
intentLogo.putExtras(extrasLogo);
PendingIntent pendingIntent = PendingIntent.getActivity(context, Widget.REQUEST_CODE, intentLogo, Intent.FLAG_ACTIVITY_NEW_TASK);
PendingIntent pendingIntent2 = PendingIntent.getActivity(context, Widget.REQUEST_CODE, intentLogo, Intent.FLAG_ACTIVITY_NEW_TASK);
PendingIntent pendingIntent3 = PendingIntent.getActivity(context, Widget.REQUEST_CODE, intentLogo, Intent.FLAG_ACTIVITY_NEW_TASK);;
