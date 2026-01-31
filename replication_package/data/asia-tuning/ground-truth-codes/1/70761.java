private Context _context;

public ConnectionDetector(Context context){
    this._context = context;
}

public boolean isConnectingToInternet(){
    ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity != null) 
      {
          NetworkInfo[] info = connectivity.getAllNetworkInfo();
          if (info != null) 
              for (int i = 0; i < info.length; i++) 
                  if (info[i].getState() == NetworkInfo.State.CONNECTED)
                  {
                      return true;
                  }

      }
      return false;
};
