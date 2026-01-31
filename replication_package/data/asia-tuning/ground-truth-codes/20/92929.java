public class Img{
    String name;
    String imgSdCardPath;
    int imgResourceValue;
};
List<Img> imageList;;
BitmapFactory.Options options = new BitmapFactory.Options();
options.inPreferredConfig = Bitmap.Config.ARGB_8888;
Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);;
