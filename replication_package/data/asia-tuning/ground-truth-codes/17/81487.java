AssetManager am = getAssets();
    String assetFileNames[] = am.list("");
    InputStream inputStream;
    for(String assetFileName : assetFileNames) {
        inputStream = am.open(assetFileName);
        // Replace with whatever you want to do with the file here
        processAssetFile(inputStream);
        inputStream.close();
    };
