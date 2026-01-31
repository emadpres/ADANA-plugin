String path = "file.txt";
FileOutputStream output = new FileOutputStream(path); 
int bufferSize = 1024;
byte[] buffer = new byte[bufferSize];
int len = 0;
while ((len = inputStream.read(buffer)) != -1) {
    output.write(buffer, 0, len);
};
