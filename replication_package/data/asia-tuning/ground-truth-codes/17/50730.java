String zipname = "data.zip";
FileInputStream fis = new FileInputStream(zipname);
ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
ZipEntry entry;

while ((entry = zis.getNextEntry()) != null) {
  System.out.println("Unzipping: " + entry.getName());

  int size;
  byte[] buffer = new byte[2048];

  FileOutputStream fos = new FileOutputStream(entry.getName());
  BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

  while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
    bos.write(buffer, 0, size);
  }
  bos.flush();
  bos.close();
}
zis.close();
fis.close();;
