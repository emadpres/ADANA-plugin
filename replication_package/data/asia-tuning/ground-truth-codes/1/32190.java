ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
   NetworkInfo[] info = cm.getAllNetworkInfo();
   for(int i=0; i <info.length; i++){
       Log.i("netinfo"+i, info[i].getType()+"");
       Log.i("netinfo"+i, info[i].getTypeName());
       Log.i("netinfo"+i, info[i].getSubtype()+"");
       Log.i("netinfo"+i, info[i].getSubtypeName());
   };
