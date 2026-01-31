EditText myTextBox = new EditText(getBaseContext());
containerLayout.addView(myTextBox);;
String value = myTextBox.getText().toString();
myTextBox.setText("this is the setted text");`;
List<EditText> myArray = new ArrayList();
EditText editText1 = new EditText(getBaseContext());
 containerLayout.addView(editText1);
 myArray.add(editText1);

EditText editText2 = new EditText(getBaseContext());
 containerLayout.addView(editText2);
 myArray.add(editText2);

EditText editText3 = new EditText(getBaseContext());
 containerLayout.addView(editText3);
 myArray.add(editText3);
 int i = 0;
    while (i < myArray.size()) {
        Log.i(TAG,myArray.get(i).getText());
        i++;
    };
