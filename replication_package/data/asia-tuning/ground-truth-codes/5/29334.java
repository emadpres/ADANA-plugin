public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {        
final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    final Button record = new Button(this); //Activity goes here
    record.setText("Record");
    record.setLayoutParams(lparams);
    record.setOnClickListener(...);
    parent.addView(record);
};
