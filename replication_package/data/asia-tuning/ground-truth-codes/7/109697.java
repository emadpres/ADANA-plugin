@Override
public void drawableStateChanged() {
    getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeOnGlobalLayoutListener( this );
            View lastChild = getChildAt( getChildCount() - 1 );
            if (lastChild != null) {
                int height = Math.max(lastChild.getBottom(), getColumnWidth());
                float child = getAdapter().getCount();
                float col = getNumColumns();
                int rows = (int) Math.ceil(child / col);
                height = rows * getColumnWidth() + (getHorizontalSpacing() * rows-1);
                setLayoutParams( new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, height ) );
            }
        }
    });
};
