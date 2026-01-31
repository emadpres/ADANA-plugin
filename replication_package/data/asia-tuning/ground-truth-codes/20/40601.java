Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.testbild);            

ImageButton btnMapLoc = (ImageButton) findViewById(R.id.imageButton1);        
LayoutParams lp = new LayoutParams(image.getWidth(), image.getHeight());
btnMapLoc.setLayoutParams(lp);

btnMapLoc.setImageBitmap(image);;
