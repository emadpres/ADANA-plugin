final PackageManager pm = getPackageManager();
//get a list of installed apps.List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {

            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG,
                    "Launch Activity :"
                            + pm.getLaunchIntentForPackage(packageInfo.packageName)); 

             // put application name to an Array, then set listviews adapter with this array
        } 
    };
