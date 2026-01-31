public static Bitmap getBitmap(String photoPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 16;
        return BitmapFactory.decodeFile(photoPath, options);
};
