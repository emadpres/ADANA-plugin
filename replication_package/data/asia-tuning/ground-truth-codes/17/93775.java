try {
    //input is your input stream object
    File file = new File(Environment.getExternalStorageDirectory(), "filename.pdf");
    OutputStream output = new FileOutputStream(file);
    try {
        try {
            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
        } finally {
            output.close();
        }
    } catch (Exception e) {
        e.printStackTrace(); // handle exception, define IOException and others
    }
} finally {
    input.close();
};
