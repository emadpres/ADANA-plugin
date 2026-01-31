BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = true;
Bitmap map = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
int originalHeight = options.outHeight;
int originalWidth = options.outWidth;
// Calculate your sampleSize based on the requiredWidth and originalWidth
// For e.g you want the width to stay consistent at 500dp
int requiredWidth = 500 * getResources().getDisplayMetrics().density;
int sampleSize = originalWidth / requiredWidth;
// If the original image is smaller than required, don't sample
if(sampleSize < 1) { sampleSize = 1; }
options.inSampleSize = sampleSize;
options.inPurgeable = true;
options.inPreferredConfig = Bitmap.Config.RGB_565;
options.inJustDecodeBounds = false;
Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);;
