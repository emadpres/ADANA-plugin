Intent intent = new Intent();
intent.setAction(android.content.Intent.ACTION_VIEW);
intent.setDataAndType(Uri.fromFile(new File(outputFileName)),"image/jpeg");
startActivity(intent);;
