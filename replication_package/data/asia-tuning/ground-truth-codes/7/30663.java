ImageView myImageView = new ImageView(context);
myImageView.setBackgroundColor(0x0); // not needed - it's the default
myImageView.setImageResource(R.drawable.indicator_icon);;
public class MyMapView extends RelativeLayout {
    private ImageView mBackImageView;
    private ImageView mIndicatorImageView;

    public MyMapView(Context context) {
        super(context);

        mBackImageView = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mBackImageView.setImageResource(R.drawable.image1);
        mBackImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(mBackImageView, params);

        mIndicatorImageView = new ImageView(context);
        RelativeLayout.LayoutParams indParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mIndicatorImageView.setImageResource(R.drawable.image2);
        addView(mIndicatorImageView, indParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        centerIndicatorPosition();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerIndicatorPosition();
    }

    // @eros: this's the part you want.this method just centers the indicator view
    // but if you have the relative position like you say, use it for the margins
    private void centerIndicatorPosition() {
        int xPos = (getMeasuredWidth() - mIndicatorImageView.getMeasuredWidth()) / 2;
        int yPos = (getMeasuredHeight() - mIndicatorImageView.getMeasuredHeight()) / 2;
        ((RelativeLayout.LayoutParams)mIndicatorImageView.getLayoutParams()).setMargins(xPos, yPos, 0, 0);
    }
};
