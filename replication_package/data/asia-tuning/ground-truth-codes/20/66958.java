options.inPreferredConfig = Config.RGB_565;
 options.inDither = true;;
public static Bitmap decodeSampledBitmapFromResource(Resources res, String resId, int    reqWidth, int reqHeight) {

 // First decode with inJustDecodeBounds=true to check dimensions
 final BitmapFactory.Options options = new BitmapFactory.Options();
 options.inJustDecodeBounds = true;
 BitmapFactory.decodeFile(resId, options);

 // Calculate inSampleSize
 options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

 // Decode bitmap with inSampleSize set
 options.inJustDecodeBounds = false;
 options.inPreferredConfig = Config.RGB_565;
 options.inDither = true;
 return BitmapFactory.decodeFile(resId, options);
 };
