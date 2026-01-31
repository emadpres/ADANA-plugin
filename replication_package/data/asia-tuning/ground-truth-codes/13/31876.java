You are overriding the loading of all URLs by forcing the WebView to load them with this code

   public boolean shouldOverrideUrlLoading(WebView view, String url) {
       Log.i(TAG, "Loading...");
       view.loadUrl(url);
       return true;
   }
The code would be something like..

Intent i = new Intent(Intent.ACTION_VIEW);
i.setData(Uri.parse(url));
startActivity(i);;
