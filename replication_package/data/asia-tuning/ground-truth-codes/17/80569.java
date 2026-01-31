public void write(Context context, String fileName, String content) throws IOException{
    FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
    outStream.write(content.getBytes());
    outStream.close();
}

public String read(Context context, String fileName) throws IOException{
    FileInputStream inStream = context.openFileInput(fileName);

    String content = null;
    byte[] readByte = new byte[inStream.available()];

    while(inStream.read(readByte) != -1){
        content = new String(readByte);
    }
    inStream.close();
    return content;
};
