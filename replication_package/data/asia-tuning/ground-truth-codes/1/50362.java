PackageManager pm = getPackageManager();
  List<ApplicationInfo> packages = 
                  pm.getInstalledApplications(PackageManager.GET_META_DATA);

  for (ApplicationInfo packageInfo : packages) {
   if(packageInfo.packageName.toLowerCase().contains(""perfect timer".toLowerCase())){

     Intent intent = 
                pm.getLaunchIntentForPackage(packageInfo.packageName);   
      if (intent != null)  {
         intent.addCategory(Intent.CATEGORY_LAUNCHER);
         startActivity(intent);  
      }  
  } else{
            // Launch google play app here
            String apppackname = "com.example.appname";
             Intent intentapp=new (Intent.ACTION_VIEW, 
                Uri.parse("market://search?q="+apppackname)));
             startActivity(intentapp);
      }       
};
