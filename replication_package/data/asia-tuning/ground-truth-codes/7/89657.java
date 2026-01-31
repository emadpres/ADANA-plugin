if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    getActivity().getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE);
    Window window = getActivity().getWindow();
    WindowManager wm = getActivity().getWindowManager();
    wm.removeViewImmediate(window.getDecorView());
    wm.addView(window.getDecorView(), window.getAttributes());

};
