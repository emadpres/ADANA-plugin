Resources res = getResources();
String mDrawableName = "logo_default";
int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
Drawable drawable = res.getDrawable(resID );
icon.setImageDrawable(drawable );;
