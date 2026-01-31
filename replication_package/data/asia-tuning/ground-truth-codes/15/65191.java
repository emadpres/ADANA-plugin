WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mView = layoutInflater.inflate(R.layout.whatToShow, null);

    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
    params.gravity = Gravity.LEFT;
    params.x = 0;
    params.y = 0;
    windowManager.addView(mView, params);;
((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mView);;
