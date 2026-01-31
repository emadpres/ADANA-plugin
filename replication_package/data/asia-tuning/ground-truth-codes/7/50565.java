View v =... ;...
v.getViewTreeObserver().addOnGlobalLayoutListener(
    new ViewTreeObserver.OnGlobalLayoutListener() {

    @SuppressWarnings("deprecation")                                                
    @SuppressLint("NewApi")
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        height = v.getHeight();
        width = v.getWidth();
    }
});;
