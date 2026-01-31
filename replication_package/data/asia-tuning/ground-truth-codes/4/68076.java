yourTextView.setOnTouchListener(new OnTouchListener(){
    @Override
    public boolean onTouch(View v, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            int lineHeight = yourTextView.getLineHeight();
            int clickedLine = (int)(event.getY() / lineHeight);
        }

        return true;
    }
});;
