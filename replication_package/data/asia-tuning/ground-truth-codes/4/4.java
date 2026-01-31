gestureView.setClickable(true);
gestureView.setFocusable(true);

GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);

gestureView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gd.onTouchEvent(motionEvent);
        return false;
    }
});