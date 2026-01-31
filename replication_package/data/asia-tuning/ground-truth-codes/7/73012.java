yourTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
   @Override
   public void onGlobalLayout() {
     int width = yourTextView.getMeasuredWidth();
     int height = yourTextView.getMeasuredHeight();

   }
});;
