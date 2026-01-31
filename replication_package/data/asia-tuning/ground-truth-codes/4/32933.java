public boolean onTouch(View v, MotionEvent event) {
    switch (event.getAction()) {
    case MotionEvent.ACTION_MOVE: 
        pager.requestDisallowInterceptTouchEvent(true);
        break;
    case MotionEvent.ACTION_UP:
    case MotionEvent.ACTION_CANCEL:
        pager.requestDisallowInterceptTouchEvent(false);
        break;
    }
};
