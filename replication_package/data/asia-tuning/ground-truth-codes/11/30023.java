Intent intent = new Intent();
intent.addCategory(Intent.CATEGORY_LAUNCHER);
intent.setAction(Intent.ACTION_MAIN);
intent.setPackage(packageName);
-->intent.setComponent(new ComponentName(packageName, className)));
ResolveInfo rInfo = getPackageManager().resolveActivity(intent, 0);

ActivityInfo aInfo = rInfo.activityInfo;;
private List<ActivityInfo> applications = new ArrayList<ActivityInfo>();

for(ApplicationInfo info:getPackageManager().getInstalledApplications(0)){
    Intent intent = new Intent();
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.setAction(Intent.ACTION_MAIN);
    intent.setPackage(info.packageName);
    List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

    for(ResolveInfo rInfo:list){                
        ActivityInfo activity = rInfo.activityInfo;
        applications.add(activity);
    }
};
