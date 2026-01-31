// class level

GestureDetector gestureDetector;
boolean tapped;
ImageView imageView;

// inside onCreate of Activity or Fragment
gestureDetector = new GestureDetector(getActivity(),new GestureListener());;
public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            tapped = !tapped;

            if (tapped) {



            } else {



            }

            return true;
        }
    };
imageView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return gestureDetector.onTouchEvent(event);
            }

        });;
