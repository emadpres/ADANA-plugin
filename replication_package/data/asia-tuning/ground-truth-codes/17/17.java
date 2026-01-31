File file = new File(context.getCacheDir(), FILENAME);
if (!file.exists()) {
    InputStream asset = context.getAssets().open(FILENAME);
    FileOutputStream output = new FileOutputStream(file);
    final byte[] buffer = new byte[1024];
    int size;
    while ((size = asset.read(buffer)) != -1) {
        output.write(buffer, 0, size);
    }
    asset.close();
    output.close();
}