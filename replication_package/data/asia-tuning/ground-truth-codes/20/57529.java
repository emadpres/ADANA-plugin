protected void onDraw(Canvas canvas) 
{
    if (cc)
    {
        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.black_square);
        Bitmap cb = Bitmap.createScaledBitmap(back, 0, 0, false);
        canvas.drawBitmap(cb,0,0,null);
        cc = false;
    } 
    else
        canvas.drawPath(path, paint);
    }
};
Paint transparent = new Paint();
transparent.setAlpha(0);;
