File file = new File(this.getFilesDir() + File.separator + "DefaultProperties.xml");
try {
        InputStream inputStream = resources.openRawResource(R.id._your_id);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        byte buf[]=new byte[1024];
        int len;
        while((len=inputStream.read(buf))>0) {
            fileOutputStream.write(buf,0,len);
        }

        fileOutputStream.close();
        inputStream.close();
    } catch (IOException e1) {};
