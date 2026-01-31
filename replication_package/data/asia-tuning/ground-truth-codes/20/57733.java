BitmapFactory.Options options = new BitmapFactory.Options();
options.inPreferredConfig = Bitmap.Config.RGB_565;
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image, options);;
