private void WriteToFileInSubfolder(Context context){
    String data = "12345";
    String subfolder = "sub";
    String filename = "file.txt";

    //Test if subfolder exists and if not create
    File folder = new File(context.getFilesDir() + File.separator + subfolder);
    if(!folder.exists()){
        folder.mkdir();
    }


    File file = new File(context.getFilesDir() + File.separator 
                         + subfolder + File.separator + filename);
    FileOutputStream outstream;

    try{
        if(!file.exists()){
            file.createNewFile();
        }

        //commented line throws an exception if filename contains a path separator
        //outstream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        outstream = new FileOutputStream(file);
        outstream.write(data.getBytes());
        outstream.close();

    }catch(IOException e){
        e.printStackTrace();
    }
};
