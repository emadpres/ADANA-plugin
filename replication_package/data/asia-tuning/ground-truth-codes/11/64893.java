String folderPath = Environnement.getExternalStorageDirectory()+"/A";

Intent intent = new Intent();  
intent.setAction(Intent.ACTION_GET_CONTENT);
Uri myUri = URI.parse(folderPath);
intent.setType("file/*");   
startActivity(intent);;
