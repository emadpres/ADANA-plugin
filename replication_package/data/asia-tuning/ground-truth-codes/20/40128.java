BitmapFactory.Options options=new BitmapFactory.Options();
options.inSampleSize=2; //try to decrease decoded image 
Bitmap bitmap=BitmapFactory.decodeStream(is, null, options); 
bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos); //compressed bitmap to file;
