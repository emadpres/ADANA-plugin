AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
if(TextUtils.equals(muteMode, "mute")){
    manager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0 , AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
}else{
    manager.setStreamVolume(AudioManager.STREAM_SYSTEM, manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) , AudioManager.FLAG_ALLOW_RINGER_MODES);
}
//here continue to take picture...;
