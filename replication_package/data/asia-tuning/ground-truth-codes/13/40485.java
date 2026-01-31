StringBuilder builder = new StringBuilder();
String[] arr = {"These","are","some","words"};
for (String s : arr){
  builder.append(s).append(" ");
textview.setText(builder.toString());;
