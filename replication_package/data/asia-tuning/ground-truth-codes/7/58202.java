final LinearLayout lL = (LinearLayout) findViewById(R.id.requirement_linear);
Button b =  new Button(this);
b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View button) {
        lL.removeView(button);
    }
});
lL.addView(b);;
lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            lL.removeViewAt(position);
        }
    });;
