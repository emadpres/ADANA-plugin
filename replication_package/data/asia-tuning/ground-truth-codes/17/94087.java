private void copyAssets() {
AssetManager assetManager = getAssets();
String[] files = null;
try {
    files = assetManager.list("");
} catch (IOException e) {
    Log.e("tag", "Failed to get asset file list.", e);
}
if (files != null) for (String filename : files) {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = assetManager.open(filename);
      File outFile = new File(Environment.getExternalStorageDirectory(), filename);
      out = new FileOutputStream(outFile);
      copyFile(in, out);
    } catch(IOException e) {
        Log.e("tag", "Failed to copy asset file: " + filename, e);
    }     
    finally {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // NOOP
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // NOOP
            }
        }
    }  
  }
}


private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
};
File dir = Environment.getExternalStorageDirectory();
File yourFile = new File(dir, "path/to/the/file/inside/the/sdcard.ext");;
