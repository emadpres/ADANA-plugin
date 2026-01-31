AssetManager assetManager = getResources().getAssets();
    String[] files = null;

    try {
        files = assetManager.list("Files");
    } catch (Exception e) {
        Log.d(MYLOG, "ERROR : " + e.toString());
    }

    for (int i = 0; i < files.length; i++) {
        InputStream in = null;
        OutputStream out = null;
        FileOutputStream fileOutStream = null;
        try {
            Log.d(MYLOG, "file names : " + files[i]);

            in = assetManager.open("Files/" + files[i]);
            out = new FileOutputStream(getApplicationContext().getFilesDir() + files[i]);

            File file = new File(getApplicationContext().getFilesDir(), files[i]);

            byte[] buffer = new byte[65536 * 2];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            fileOutStream = new FileOutputStream(file);
            fileOutStream.write(buffer);
            out.close();
            out = null;
            Log.d(MYLOG, "File Copied in storage");
        } catch (Exception e) {
            Log.d(MYLOG, "ERROR: " + e.toString());
        }
    };
