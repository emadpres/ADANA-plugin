public class UpdateReceiver extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent intent) {


      ConnectivityManager connectivityManager = (ConnectivityManager) 
                                   context.getSystemService(Context.CONNECTIVITY_SERVICE );
      NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();   
      if (isConnected)       
          Log.i("NET", "connecte" +isConnected);   
      else Log.i("NET", "not connecte" +isConnected);
    }
};
