public void makeView(Context context) {
    RelativeLayout rl = new RelativeLayout(context);
    TextView tv = new TextView(context);
    tv.setText("Hello, World");

    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
            (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

    rl.addView(tv, lp);
};
