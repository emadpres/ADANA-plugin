File f = new File(getCacheDir()+"/Introduction.pdf");
if (!f.exists()) 
try {

  InputStream is = getAssets().open("Introduction.pdf");
  byte[] buffer = new byte[1024];
  is.read(buffer);
  is.close();


  FileOutputStream fos = new FileOutputStream(f);
  fos.write(buffer);
  fos.close();
} catch (Exception e) { throw new RuntimeException(e); };
