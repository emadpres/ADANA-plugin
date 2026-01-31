gestureDetector = new GestureDetector(this);;
@Override
   public boolean onTouchEvent(MotionEvent me) {
    return gestureDetector.onTouchEvent(me);
};
