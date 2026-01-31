AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);   

switch (am.getRingerMode()) {   
    case AudioManager.RINGER_MODE_SILENT:   
        Log.i("MyApp","Silent mode");
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        break;   
};
