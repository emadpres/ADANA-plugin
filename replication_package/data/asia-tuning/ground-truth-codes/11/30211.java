Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
intent.setType("image/*"); 
startActivityForResult(intent, IMAGE_PICK);;
