@Override
public boolean onInterceptTouchEvent (MotionEvent ev){
    if(horizontalSwipe(ev)){
        return true;
    }
    else
        return false;

};
