actv.setThreshold(1000);;
actv.setOnTouchListener(new OnTouchListener() {
                @Override
    public boolean onTouch(View v, MotionEvent event) {
        actv.setThreshold(1);
        return false;
    }
});;
