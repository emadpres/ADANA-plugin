public class MyActivity extends Activity implements View.OnTouchListener{

     private RelativeLayout someLayout;
     //take any layout on which you want your gesture listener;

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    gestureDetector=new GestureDetector(this,new OnSwipeListener(){

        @Override
        public boolean onSwipe(Direction direction) {
            if (direction==Direction.up){
               //do your stuff
                Log.d(TAG, "onSwipe: up");

            }

            if (direction==Direction.down){
               //do your stuff
                Log.d(TAG, "onSwipe: down");
            }
            return true;
        }


    });
    someLayout.setOnTouchListener(this);
}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      Log.d(TAG, "onTouch: ");
      gestureDetector.onTouchEvent(event);
      return true;
  }


};
