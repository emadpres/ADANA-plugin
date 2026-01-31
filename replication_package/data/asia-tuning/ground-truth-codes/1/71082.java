private class NetworkUtilTask extends AsyncTask<Void, Void, Boolean>{
    Context context;

    public NetworkUtilTask(Context context){
        this.context = context;
     }

     protected Boolean doInBackground(Void... params) {
         return hasActiveInternetConnection(this.context);
     }
     protected void onPostExecute(Boolean hasActiveConnection) {
         Log.d(LOG_TAG,"Success=" + hasActiveConnection);
     }
 };
NetworkUtilTask netTask = new NetworkUtilTask(context);
netTask.execute();;
