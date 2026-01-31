Intent intent = new Intent();
intent.setAction(android.content.Intent.ACTION_VIEW);
File file = new File(path);
intent.setDataAndType(Uri.fromFile(file), "application/zip");
startActivity(intent);;
