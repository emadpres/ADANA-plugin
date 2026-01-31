BitmapFactory.Options bmOptions = new BitmapFactory.Options();
bmOptions.inSampleSize = 1; // 1 = 100% if you write 4 means 1/4 = 25% 
bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent(),
                                                                   null, bmOptions);
bmImage.setImageBitmap(bitmap);;
