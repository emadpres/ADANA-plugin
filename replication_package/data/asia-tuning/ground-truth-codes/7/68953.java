btn = (Button) findViewById(R.id.bn);   
    btn.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {

            new AsyncTaskTimer.execute();

        }
    });;
btn.getViewTreeObserver().removeOnGlobalLayoutListener(this);;
