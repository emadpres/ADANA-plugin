public void onReceive(Context context, Intent intent) {
    KeyguardManager kg = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    Boolean screenBlocked = !kg.inKeyguardRestrictedInputMode();
    Log.w(TAG, "Screen lock is " + screenBlocked.toString());;
