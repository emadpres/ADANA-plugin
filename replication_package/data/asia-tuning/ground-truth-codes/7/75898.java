button1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                       button1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        button1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                   //here the size is already available.create new button2 here with the size of button1
                }
            });;
