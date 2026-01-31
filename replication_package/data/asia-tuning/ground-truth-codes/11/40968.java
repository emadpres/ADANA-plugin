Intent mmsIntent = new Intent(Intent.ACTION_SEND);
//file is the file on the SD Card
mmsIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.toURL().toString()));
mmsIntent.setType("image/png");//mmsIntent.setType("image/*"); Maybe?
startActivity(mmsIntent);;
