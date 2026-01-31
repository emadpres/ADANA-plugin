private GestureDetector gestureDetector;
private View.OnTouchListener gestureListener;
SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        float sensitvity = 10;

        // TODO Auto-generated method stub
        if((e1.getX() - e2.getX()) > sensitvity){
            //Swipe Left
        } else if((e2.getX() - e1.getX()) > sensitvity){
            //Swipe Right
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }
};;
gestureDetector = new GestureDetector(getActivity(), simpleOnGestureListener);;
gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
view.setOnTouchListener(gestureListener);;
