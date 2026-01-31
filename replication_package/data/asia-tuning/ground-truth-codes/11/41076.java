public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){      
   //context.startService(new Intent(context, YourService.class));

   //Start activity as:
    Intent intent24 = new Intent(Intent.ACTION_MAIN).addCategory(
    Intent.CATEGORY_LAUNCHER).setClassName("YOUR_PACKAGE_NAME",
    "com.YOUR_PACKAGE_NAME..YOURACTIVITY_NAME").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_FROM_BACKGROUND).setComponent(new ComponentName("YOUR_PACKAGE_NAME",
    "com.YOUR_PACKAGE_NAME..YOURACTIVITY_NAME"));
    context.startActivity(intent24);
   }
 }
};
