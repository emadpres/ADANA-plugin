public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";

    private ScrollView mScroll = null;
    private ImageView mImage = null;

    private ViewTreeObserver.OnGlobalLayoutListener mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final Rect imageRect = new Rect(0, 0, mImage.getWidth(), mImage.getHeight());
            final Rect imageVisibleRect = new Rect(imageRect);

            mScroll.getChildVisibleRect(mImage, imageVisibleRect, null);

            if (imageVisibleRect.height() < imageRect.height() ||
                    imageVisibleRect.width() < imageRect.width()) {
                Log.w(TAG, "image is not fully visible");
            } else {
                Log.w(TAG, "image is fully visible");
            }

            mScroll.getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutListener);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the layout with the test view
        setContentView(R.layout.main);

        mScroll = (ScrollView) findViewById(R.id.scroller);
        mImage = (ImageView) findViewById(R.id.image);

        mScroll.getViewTreeObserver().addOnGlobalLayoutListener(mLayoutListener);
    }
};
