AssetManager am = getAssets();
InputStream inputStream = am.open("myfoldername/myfilename");
File file = createFileFromInputStream(inputStream);

private File createFileFromInputStream(InputStream inputStream) {

   try{
      File f = new File(my_file_name);
      OutputStream outputStream = new FileOutputStream(f);
      byte buffer[] = new byte[1024];
      int length = 0;

      while((length=inputStream.read(buffer)) > 0) {
        outputStream.write(buffer,0,length);
      }

      outputStream.close();
      inputStream.close();

      return f;
   }catch (IOException e) {
         //Logging exception
   }

   return null;
};
