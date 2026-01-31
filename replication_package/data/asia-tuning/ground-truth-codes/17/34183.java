File f = new File(getCacheDir()+"/m1.map");
  if (!f.exists()) try {

    InputStream is = getAssets().open("m1.map");
    int size = is.available();
    byte[] buffer = new byte[size];
    is.read(buffer);
    is.close();


    FileOutputStream fos = new FileOutputStream(f);
    fos.write(buffer);
    fos.close();
  } catch (Exception e) { throw new RuntimeException(e); }

  mapView.setMapFile(f.getPath());;
