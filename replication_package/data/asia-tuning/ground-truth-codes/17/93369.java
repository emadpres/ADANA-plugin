String TAG = "CLIPPYDATA-";
    String content = "The quick brown fox jumps over the lazy dog."; //size not fixed, sample purpose only.File outputFile  = new File("/path/to/file");
    long fileLength = outputFile.length();
    FileOutputStream output = new FileOutputStream(outputFile, true);
    try {
        output.write((TAG + content).getBytes());
        byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(fileLength).array();
        output.write(bytes);
    } finally {
        //output.flush(); (should I?)
        output.close();
    };
RandomAccessFile raf = new RandomAccessFile("/path/to/file", "rb");
    long endPositon = raf.length() - Long.SIZE / Byte.SIZE;
    // get last 8 bytes
    raf.seek(endPositon);
    long tagPosition = raf.readLong();
    raf.seek(tagPosition);
    byte[] bytes = new byte[endPositon - tagPosition];
    raf.read(bytes);
    String appendedData = new String(bytes);
    if (appendedData.startsWith(TAG)) {
        // appendedData is what you want
    };
