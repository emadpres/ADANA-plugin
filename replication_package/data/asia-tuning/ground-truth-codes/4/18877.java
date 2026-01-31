import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class TouchUtil {

    public static void addDoubleTapListenerToView(final View target, final Runnable action){
        final GestureDetector gestureDetector = new GestureDetector(target.getContext(), new GestureDetector.SimpleOnGestureListener() {

            // must to process doubleTap event
            @Override
            public boolean onDown(MotionEvent e) {
                Log.w(getClass().getSimpleName(), "onDown");
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.w(getClass().getSimpleName(), "onDoubleTap");
                // run handling code on ui thread
                target.post(action);
                return true;
            }
        });

        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

}


//// usage ////
logo = (ImageView) findViewById(R.id.logo);
TouchUtil.addDoubleTapListenerToView(logo, new Runnable() {
	@Override
	public void run() {
		Toast.makeText(getContext(), "double tap!", Toast.LENGTH_SHORT).show();
	}
});