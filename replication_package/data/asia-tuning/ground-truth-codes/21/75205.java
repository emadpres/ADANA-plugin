public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int i = 0; i < appWidgetIds.length; i++) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent appIntent = new Intent(context, MyActivity.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, appIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_button, appPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds[i], views);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);
};
