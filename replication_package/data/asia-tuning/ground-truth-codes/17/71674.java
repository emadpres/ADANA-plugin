InputStream inputStream = am.open(inputFile);;
File f = context.getFileStreamPath("filename.txt");
OutputStream outputStream = new FileOutputStream(f);;
ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 8);

ReadableByteChannel ich = Channels.newChannel(inputStream);
WritableByteChannel och = Channels.newChannel(outputStream);

while (ich.read(buffer) > -1 || buffer.position() > 0)
{
    buffer.flip();
    och.write(buffer);
    buffer.compact();
}   
ich.close();
och.close();;
File f = context.getFileStreamPath("filename.txt");
FileReader fr = new FileReader(f);
int chr = fr.read(); // read char
fr.close();;
File f = context.getFileStreamPath("filename.txt");
FileWriter fw = new FileWriter(f);
fw.write("word"); // write string
fw.close();;
