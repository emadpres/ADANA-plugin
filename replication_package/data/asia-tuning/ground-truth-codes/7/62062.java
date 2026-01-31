final ViewTreeObserver vto = view.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            if (Build.VERSION.SDK_INT < 16) {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            width = view.getWidth();
            height = view.getHeight();

            // using the method Al cio mention
            setupOtherViewsThatDependOnWidthAndHeight();

        }
    });;
