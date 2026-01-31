public boolean importDatabase(String dbPath) throws IOException {
    // Close the SQLiteOpenHelper so it will commit the created empty
    // database to internal storage.close();
    File newDb = new File(dbPath);
    File oldDb = new File(DB_FILEPATH);
    if (newDb.exists()) {
        FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
        // Access the copied database so SQLiteHelper will cache it and mark
        // it as created.DBHelper.getWritableDatabase().close();
        return true;
    }
    return false;
};
public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
    FileChannel fromChannel = null;
    FileChannel toChannel = null;
    try {
        fromChannel = fromFile.getChannel();
        toChannel = toFile.getChannel();
        fromChannel.transferTo(0, fromChannel.size(), toChannel);
    } finally {
        try {
            if (fromChannel != null) {
                fromChannel.close();
            }
        } finally {
            if (toChannel != null) {
                toChannel.close();
            }
        }
    }
};
