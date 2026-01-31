BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

Bitmap btemp = BitmapFactory.decodeFile(selectedImagePath,options);;
options.outHeight     for height
      options.outWidth    for width;
