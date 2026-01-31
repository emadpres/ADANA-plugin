Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inScaled = false;
            Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.your_image, options);

            final int maxSize = 960; //set this to the size you want in pixels
            int outWidth;
            int outHeight;
            int inWidth = source.getWidth();
            int inHeight = source.getHeight();
            if(inWidth > inHeight){
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth; 
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight; 
            }

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, outWidth, outHeight, false);
            picture = (ImageView)v.getTag(R.id.picture);
            picture.setImageBitmap(resizedBitmap);;
