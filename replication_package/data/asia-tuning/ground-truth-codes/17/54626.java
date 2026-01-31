public static void pipe(InputStream is, OutputStream os) throws IOException {

        int read = -1;
        byte[] buf = new byte[1024];

        try {
            while( (read = is.read(buf)) != -1) {
                os.write(buf, 0, read);
            }
        }
        finally {
            is.close();
            os.close();
        }
    };
