yourView.setOnTouchListener(new OnTouchListener() {
private GestureDetector gestureDetector = new GestureDetector(Test.this, new GestureDetector.SimpleOnGestureListener() {
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("TEST", "onDoubleTap");
        return super.onDoubleTap(e);
    }... // implement here other callback methods like onFling, onScroll as necessary
});

@Override
public boolean onTouch(View v, MotionEvent event) {
    Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
    gestureDetector.onTouchEvent(event);
    return true;
}});;
