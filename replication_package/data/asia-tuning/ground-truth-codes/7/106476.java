public void onCreate(Bundle savedInstanceState)
{
     super.onCreate(savedInstanceState);

     // inflate your main layout here (use RelativeLayout or whatever your root ViewGroup type is
     LinearLayout mainLayout = (LinearLayout ) this.getLayoutInflater().inflate(R.layout.main, null); 

     // set a global layout listener which will be called when the layout pass is completed and the view is drawn
     mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
     new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
               //Remove the listener before proceeding
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
               } else {
                    mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
               }

               // measure your views here
          }
     }
 );

 setContentView(mainLayout);;
