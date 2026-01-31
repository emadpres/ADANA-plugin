byte[] imageAsBytes = Base64.decode(myImageData.getBytes());
Bitmap decodedByte = BitmapFactory.decodeByteArray(imageAsBytes , 0, imageAsBytes.length);;
