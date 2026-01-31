ObjectOutputStream objectOutputStream = null;
    try {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                getCacheDir() + File.separator + "object"));
        MyObject object = new MyObject();
        object.setData(15);
        object.setName("name");
        objectOutputStream.writeObject(new MyObject());
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } finally {
        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
