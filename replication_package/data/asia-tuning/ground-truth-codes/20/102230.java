Options options = new BitmapFactory.Options();
options.inScaled = false;
Bitmap source = BitmapFactory.decodeResource(a.getResources(), path, options);
ImageView mImg;
mImg = (ImageView) findViewById(R.id.imageView);
mImg.setImageBitmap(source);;
