@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    //TouchEvent dispatcher.if (gestureDetector != null) {
        if (gestureDetector.onTouchEvent(ev))
            //If the gestureDetector handles the event, a swipe has been executed and no more needs to be done.return true;
    }
    return super.dispatchTouchEvent(ev);
}

@Override
public boolean onTouchEvent(MotionEvent event) {
    return gestureDetector.onTouchEvent(event);
};
