final View view =...; // The view of which you want to get the location
view.getViewTreeObserver().addOnGlobalLayoutListener(
    new ViewTreeObserver.OnGlobalLayoutListener() {

      @Override
      public void onGlobalLayout() {
        // here you can get the View's location
        int test1[] = new int[2];
        view.getLocationInWindow(test1);

        int test2[] = new int[2];
        view.getLocationOnScreen(test2);
      }
    });;
