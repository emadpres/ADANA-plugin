File file =...;
FileInputStream fis = new FileInputStream(file); // or FileOutputStream fos = new FileOutputStream(file);
FileLock lock = fis.getChannel().lock(); // or FileLock lock = fos.getChannel().lock();

// do whatever you want with the file

lock.release();;
