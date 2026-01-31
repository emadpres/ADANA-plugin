WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);;
GestureDetector gestureDetector = new GestureDetector(this, new AwesomeGestureListener());
View.OnTouchListener gestureListener = new View.OnTouchListener() {
      public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
      }
};

overlayView.setOnTouchListener(gestureListener);;
