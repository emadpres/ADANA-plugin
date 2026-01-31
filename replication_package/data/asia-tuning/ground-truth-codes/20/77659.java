public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
  // Raw height and width of image
  final int height = options.outHeight;
  final int width = options.outWidth;
  int inSampleSize = 1;

  if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.while ((halfHeight / inSampleSize) > reqHeight
            && (halfWidth / inSampleSize) > reqWidth) {
          inSampleSize *= 2;
         }
   }

return inSampleSize;
};
// create new BitmapFactory.Options 
    BitmapFactory.Options options = new BitmapFactory.Options();

    options.inJustDecodeBounds = true; 

    BitmapFactory.decodeFile(imagePath, options);

    // calculate inSampleSize, 1024 is your request-width, 768 is your request-height 
    options.inSampleSize = calculateInSampleSize(options, 1024, 768);

    // decode bitmap with inSampleSize set 
    options.inJustDecodeBounds = false; 

    Bitmap imgBitmap = BitmapFactory.decodeFile(imagePath, options);

    // set image bitmap after alter bitmap height and width
    imgDetail.setImageBitmap(imgBitmap);;
