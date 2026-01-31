ViewTreeObserver vto = layout.getViewTreeObserver();
     vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
         @Override
             public void onGlobalLayout() {
                 //You should be able to get the width and height over here.layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
             }
     });;
