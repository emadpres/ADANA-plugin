BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeFile(path, options);
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));;
