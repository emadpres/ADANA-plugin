final OutputStream out = /* open your file using a FileOutputStream here */;

final byte[] buf = new byte[8096]; // size as appropriate

// "in" is the InputStream from the socket
int count;

try {
    while ((count = in.read(buf)) != -1)
        out.write(buf, 0, count);

    out.flush();
} finally {
    out.close();
};
