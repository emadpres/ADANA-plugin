private void moveFile(String inputPath, String outputPath) {

    System.out.println(inputPath);
    System.out.println(outputPath);



    InputStream in = null;
    OutputStream out = null;
    try {

        // create output directory if it doesn't exist
        File dir = new File(outputPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        in = new FileInputStream(inputPath);
        out = new FileOutputStream(outputPath);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        in = null;

        // write the output file
        out.flush();
        out.close();
        out = null;

        // delete the original file
        new File(inputPath).delete();

    }catch (FileNotFoundException fnfe1) {
        Log.e("File not found exception", fnfe1.getMessage());
    } catch (Exception e) {
        Log.e("Other Exception", e.getMessage());
    }

};
moveFile(Environment.getExternalStorageDirectory() + "/download/abc.txt", Environment.getExternalStorageDirectory() + "/abc.txt");;
