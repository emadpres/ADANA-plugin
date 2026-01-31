Intent myIntent = new Intent(PresentActivity.this, AlarmActivity.class);          
pendingIntent = PendingIntent.getActivity(PresentActivity.this,pending_intent_unique_id, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
pendingIntent.cancel();
alarmManager.cancel(pendingIntent);;
