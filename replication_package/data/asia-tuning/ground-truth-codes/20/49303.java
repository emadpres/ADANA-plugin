File directory = new File(extStorageDirectory, "myFolder");
File fileInDirectory = new File(directory, files[which]);
Bitmap bitmap = BitmapFactory.decodeFile(fileInDirectory.getAbsolutePath());;
