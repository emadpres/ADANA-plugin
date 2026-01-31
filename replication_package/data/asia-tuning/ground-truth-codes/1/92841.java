public class MediaPlayerDemo_Audio extends Activity
{

//  your activity code.....
//  


// here is your broadcast receiver 
public class ConnectivityChangeReceiver extends BroadcastReceiver {

@SuppressWarnings("deprecation")
@Override
public void onReceive(Context context, Intent intent) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE );
    /* Check wi-fi network availability */
    NetworkInfo activeWifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    boolean isConnectedToWifi = activeWifiInfo != null && activeWifiInfo.isConnectedOrConnecting();
    /* Check mobile network availability */
    NetworkInfo activeMobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    boolean isConnectedToMobileData = activeMobileInfo != null && activeMobileInfo.isConnectedOrConnecting();

    if(isConnectedToWifi){
        //Toast.makeText(context, "Connected to wifi!", Toast.LENGTH_LONG).show();
    }else{
        if(isConnectedToMobileData){
            //Toast.makeText(context, "Connected to Mobile data!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "No internet connection!", Toast.LENGTH_LONG).show();
            /* Here I want to finish that activity */

//finish activity here 

        }
    }       
}}



};
