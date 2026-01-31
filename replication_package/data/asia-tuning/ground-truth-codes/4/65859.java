public class GestureDoubleTap extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //some logic
        return true;
    }

};
GestureDoubleTap gestureDoubleTap = new GestureDoubleTap();
gestureDetector = new GestureDetector(this/* context */, gestureDoubleTap);

view.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

});;
