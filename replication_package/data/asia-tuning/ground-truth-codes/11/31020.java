Intent intent = new Intent();
intent.setType("image/*");
intent.setAction(Intent.ACTION_GET_CONTENT);//
startActivityForResult(Intent.createChooser(intent, "Select Picture"),10);;
