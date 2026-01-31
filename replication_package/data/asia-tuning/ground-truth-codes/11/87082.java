public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent msgIntent = new Intent(context, ReminderService.class);
        msgIntent.setAction(ReminderService.ACTION_RESCHEDULE);
        context.startService(msgIntent);
    }
};
