AlarmManager[] alarmManager=new AlarmManager[24];
intentArray = new ArrayList<PendingIntent>();
for(f=0;f<arr2.length;f++){
   Intent intent = new Intent(AlarmR.this, Riciving.class);
   pi=PendingIntent.getBroadcast(AlarmR.this, f,intent, 0);

   alarmManager[f] = (AlarmManager) getSystemService(ALARM_SERVICE);
   alarmManager[f].set(AlarmManager.RTC_WAKEUP,arr2[f] ,pi);

   intentArray.add(pi);

};
private void cancelAlarms(){
    if(intentArray.size()>0){
        for(int i=0; i<intentArray.size(); i++){
            alarmmanager.cancel(intentArray.get(i));
        }
        intentArray.clear();
    }
};
