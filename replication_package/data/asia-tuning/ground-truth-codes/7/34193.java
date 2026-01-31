View myView=findViewById(R.id.myView);
  myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //At this point the layout is complete and the 
                //dimensions of myView and any child views are known.}
        });;
