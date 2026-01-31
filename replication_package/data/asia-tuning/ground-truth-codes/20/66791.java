List<Bitmap> imgList = new ArrayList<Bitmap>();

        for ( int i=1 ; i<=n ; i++)
        {
            imgList.add(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("num" + i, "drawable", getPackageName()))); 
        };
