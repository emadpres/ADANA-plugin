button2.setEnabled(false);;
button2.setEnabled(true);;
button2.setEnabled(false);

private OnClickListener l = new OnClickListener() {
    public void onClick(View v) {
      button2.setEnabled(true);
    }
};

button1.setOnClickListener(l);;
