class DoubleTapGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("TAG", "Double Tap Detected...");
            return true;
        }

    };
final GestureDetector mGesDetect = new GestureDetector(this, new DoubleTapGestureDetector());;
surfview.setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGesDetect.onTouchEvent(event);
            return true;
        }
    });;
