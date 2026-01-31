Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setDataAndType(Uri.parse("http://www.yourvideo.mp4"), Req_code);
startActivityForResult(intent);;
