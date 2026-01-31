protected static final float LARGE_MOVE = 60;
private SimpleOnGestureListener gestureListener;
private GestureDetector gestureDetector;

gestureListener = new SimpleOnGestureListener() {
            @Override
            public boolean onFling(android.view.MotionEvent e1,
                    android.view.MotionEvent e2, float velocityX,
                    float velocityY) {

                int _index = //your current image
                int _size = //number of images you have to swipe through

                if (e1.getX() - e2.getX() > LARGE_MOVE) {
                    // Log.d("swipe", "SWIPE LEFT");
                    _index += 1;
                    if (_index > _size - 1) {
                        _index = 0;
                    }

                    return true;
                } else if (e2.getX() - e1.getX() > LARGE_MOVE) {
                    // Log.d("swipe", "SWIPE RIGHT");
                    _index -= 1;
                    if (_index < 0) {
                        _index = _size - 1;
                    }
                    return true;

                }
                return false;
            };
        };
        gestureDetector = new GestureDetector(this, gestureListener);;
@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    };
