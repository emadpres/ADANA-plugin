src/androidTest/assets/sometestfile
src/main/assets/someappfile;
@Test
public final void testAccessToAppAssetsFromTest() throws IOException {
    final AssetManager assetManager = mInstrumentation.getTargetContext().getAssets();
    assetManager.open("someappfile");
}

@Test
public final void testAccessToTestAssetsFromTest() throws IOException {
    final AssetManager assetManager = mInstrumentation.getContext().getAssets();
    assetManager.open("sometestfile");
};
