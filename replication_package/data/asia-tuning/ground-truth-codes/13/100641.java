final int VIDEO_REQUEST_CODE = 5000;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_puzzle);

    Intent intent = new Intent(Intent.ACTION_PICK, 
                               MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

    startActivityForResult(intent, VIDEO_REQUEST_CODE);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == VIDEO_REQUEST_CODE)
    {
        Uri videoUri = data.getData();

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        ContentResolver resolver = this.getContentResolver();
        String type = mime.getExtensionFromMimeType(resolver.getType(videoUri));

        intent.setDataAndType(videoUri, type);

        this.startActivity(intent);
    }
};
