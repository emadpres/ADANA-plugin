public static boolean checkVibrationIsOn(Context context){
    boolean status = false;
    AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
        status = true;
    } else if (1 == Settings.System.getInt(context.getContentResolver(), "vibrate_when_ringing", 0)) //vibrate on
        status = true;
    return status;
};
public static boolean checkRingerIsOn(Context context){
    AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    return am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
};
