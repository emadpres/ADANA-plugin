public void backup() {
    try {
        File sdcard = Environment.getExternalStorageDirectory();
        File outputFile = new File(sdcard,
                "YourDB.bak");

        if (!outputFile.exists()) 
             outputFile.createNewFile(); 

        File data = Environment.getDataDirectory();
        File inputFile = new File(data, "data/your.package.name/databases/yourDB");
        InputStream input = new FileInputStream(inputFile);
        OutputStream output = new FileOutputStream(outputFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    } catch (IOException e) {
        e.printStackTrace();
        throw new Error("Copying Failed");
    }
};
