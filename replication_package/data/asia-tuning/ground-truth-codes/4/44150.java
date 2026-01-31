public final OnTouchListener dummyOnTouchListener = new OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent rawEvent) {
        return false;
    }
};;
yourView.setOnTouchListener(dummyOnTouchListener);;
