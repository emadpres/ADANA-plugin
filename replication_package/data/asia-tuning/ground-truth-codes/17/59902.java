public static void copyDbToSd(Context context) {
    File dbFile = context.getDatabasePath((String) DatabaseHandler.DATABASE_NAME);


    InputStream myInput;
    try {
        myInput = new FileInputStream(dbFile);
        OutputStream myOutput = new FileOutputStream(
                Environment.getExternalStorageDirectory() 
                + java.io.File.separator 
                + "database.db");

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    } catch (FileNotFoundException e) {
        Log.e(TAG, "Exception: ", e);
    } catch (IOException e) {
        Log.e(TAG, "Exception: ", e);
    }

};
