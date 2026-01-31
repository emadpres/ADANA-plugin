BitmapFactory.Options options=new BitmapFactory.Options();
options.inSampleSize = 8;
Bitmap preview_bitmap=BitmapFactory.decodeStream(is,null,options);;
