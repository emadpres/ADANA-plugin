public void loadGLTexture(GL10 gl, Context context, int resourceId) {
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

    gl.glGenTextures(1, textures, 0);
    gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
    gl.gltexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
    gl.gltexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    bitmap.recycle();
};
