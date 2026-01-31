String FILENAME = "hello_file";
String string = "hello world!";

FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
fos.write(string.getBytes());
fos.close();;
