youintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
public class AlarmService extends Service {

private Intent alarmIntent;

@Override
public void onCreate() {
    alarmIntent = new Intent(this, AlarmActivity.class);
    alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
}

@Override
public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);

    //Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG)
    //.show();

    startActivity(alarmIntent);
}

// another implement method......
};
