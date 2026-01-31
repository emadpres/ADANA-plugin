imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

    @Override
    public void onGlobalLayout() {

        textView.getLayoutParams().width = imageView.getMeasuredWidth();

        if (Build.VERSION.SDK_INT >= 16) {
            imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
});;
